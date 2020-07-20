package pcoop.backend.controller;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import pcoop.backend.dto.DirectoryDTO;
import pcoop.backend.dto.FileDTO;
import pcoop.backend.dto.MemberDTO;
import pcoop.backend.dto.ProjectDTO;
import pcoop.backend.service.FileService;

@Controller
public class FileController {

	@Autowired
	HttpSession session;
	@Autowired
	FileService fservice;

	@RequestMapping("fileList")
	public String fileList(Model model) {

		// 드라이브에서 직접 목록 가져올 때
		// String rootDir = session.getServletContext().getRealPath("upload/backup/" + project_name);
		// List<String> dirList = fservice.getDirListFromDrive(rootDir);
		// List<String> fileList = fservice.getFileListFromDrive(rootDir);
		//		for(String s : dirList) {
		//			JsonObject json = new JsonObject();
		//			json.addProperty("seq", 1);
		//			json.addProperty("path", s);
		//			dirArr.add(json);
		//
		//		}
		//		
		//		for(String s : fileList) {
		//			System.out.println(s);
		//			JsonObject json = new JsonObject();
		//			json.addProperty("seq", 1);
		//			json.addProperty("path", s);
		//			fileArr.add(json);
		//
		//		}

		// DB에서 목록 가져올 때
		ProjectDTO project = (ProjectDTO) session.getAttribute("projectInfo");
		List<DirectoryDTO> dirList = fservice.getDirList(project.getSeq());
		JsonArray dirArr = new JsonArray();

		for(DirectoryDTO dto : dirList) {
			JsonObject json = new JsonObject();
			json.addProperty("seq", dto.getSeq());
			json.addProperty("path", dto.getPath());
			dirArr.add(json);
		}

		model.addAttribute("dirlist", new Gson().toJson(dirArr));
		return "backup/fileList";

	}

	@RequestMapping("getParentDirSeq")
	@ResponseBody
	public String getParentDirSeq(int dir_seq) {
		
		int back_dir_seq = fservice.getParentSeqBySeq(dir_seq);
		return Integer.toString(back_dir_seq);
	}

	@RequestMapping(value = "getDirAndFileList", produces = "application/text; charset=utf8")
	@ResponseBody
	public String getDirAndFileList(int dir_seq) {
		ProjectDTO project = (ProjectDTO) session.getAttribute("projectInfo");
		int root_seq = fservice.getRootDirSeq(project.getSeq());
		String path = fservice.getDirPathBySeq(dir_seq);
		List<DirectoryDTO> dirList = fservice.getDirList(dir_seq);
		List<FileDTO> fileList = fservice.getFileListByDirSeq(dir_seq);
		JsonArray dirArr = new JsonArray();
		JsonArray fileArr = new JsonArray();

		for(DirectoryDTO dto : dirList) {
			JsonObject json = new JsonObject();
			json.addProperty("seq", dto.getSeq());
			// json.addProperty("project_seq", dto.getProject_seq());
			json.addProperty("parent_seq", dto.getParent_seq());
			json.addProperty("name", dto.getName());
			json.addProperty("path", dto.getPath());
			json.addProperty("root_yn", dto.getRoot_yn());
			dirArr.add(json);
		}

		for(FileDTO dto : fileList) {
			JsonObject json = new JsonObject();
			json.addProperty("seq", dto.getSeq());
			json.addProperty("path", dto.getPath());
			json.addProperty("name", dto.getName());
			json.addProperty("text_yn", dto.getText_yn());
			fileArr.add(json);
		}

		JsonObject data = new JsonObject();
		data.addProperty("root_seq", root_seq);
		data.addProperty("path", path);
		data.addProperty("dirArr", new Gson().toJson(dirArr));
		data.addProperty("fileArr", new Gson().toJson(fileArr));


		return new Gson().toJson(data);
	}

	@RequestMapping(value = "checkExists")
	@ResponseBody
	@Transactional("txManager")
	public int checkExists(String type, int seq) {

		// 삭제됐는지 확인, 안 됐으면 리턴 0
		// 됐으면 상위 디렉토리 seq 리턴
		int isDeleted = fservice.checkDeleteLog(type, seq);
		int dir_seq = 0;
		
		// 삭제 안 됐으면 리턴 0
		if(isDeleted == 0)
			return 0;
		
		// 삭제됐으면 상위 디렉토리 찾아서 seq 리턴
		else {
			
			while(isDeleted > 0) {
				System.out.println(seq);
				dir_seq = fservice.getParentSeqByDeleteLog(type, seq);
				isDeleted = fservice.checkDeleteLog(type, dir_seq);
				seq = dir_seq;
			}
			
			return seq;

		}
		
	}

	@RequestMapping(value = "addDirectory", produces = "application/text; charset=utf8")
	@ResponseBody
	@Transactional("txManager")
	public String addDirectory(int parent_seq, String name) {

		MemberDTO member = (MemberDTO) session.getAttribute("loginInfo");
		ProjectDTO project = (ProjectDTO) session.getAttribute("projectInfo");
		JsonObject data = new JsonObject();

		// 프로젝트의 루트 디렉토리 seq 가져옴
		int root_seq = fservice.getRootDirSeq(project.getSeq());
		JsonArray dirArr = new JsonArray();

		// 디렉토리 이름 중복 확인
		int checkDupl = fservice.checkDuplDirName(parent_seq, name);

		// 중복이 아니라면
		if(checkDupl == 0) {

			// 드라이브에 디렉토리 생성
			String path = fservice.makeDirToDrive(parent_seq, name);
			// DB에 디렉토리 insert
			fservice.insertDirectory(path, name, project.getSeq(), parent_seq, member.getName());
			int newDirSeq = fservice.getDirSeqByName(name, parent_seq);

			// 업데이트된 리스트 보내기
			List<DirectoryDTO> dirList = fservice.getDirList(root_seq);

			for(DirectoryDTO dto : dirList) {
				JsonObject json = new JsonObject();
				json.addProperty("seq", dto.getSeq());
				json.addProperty("name", dto.getName());
				json.addProperty("path", dto.getPath());
				dirArr.add(json);
			}

			data.addProperty("seq", newDirSeq);
			data.addProperty("dirlist", new Gson().toJson(dirArr));

		}

		data.addProperty("checkDupl", checkDupl);
		return new Gson().toJson(data);

	}

	@RequestMapping(value = "deleteDirectory", produces = "application/text; charset=utf8")
	@ResponseBody
	public String deleteDirectory(int seq, int root_seq) {

		MemberDTO member = (MemberDTO) session.getAttribute("loginInfo");

		int parent_seq = fservice.getParentSeqBySeq(seq);
		String path = fservice.getDirPathBySeq(seq);
		fservice.deleteDirectory(seq, parent_seq, path, member.getName());
		
		// 업데이트된 리스트 보내기
		List<DirectoryDTO> dirList = fservice.getDirList(root_seq);
		JsonArray dirArr = new JsonArray();

		for(DirectoryDTO dto : dirList) {
			JsonObject json = new JsonObject();
			json.addProperty("seq", dto.getSeq());
			json.addProperty("name", dto.getName());

			json.addProperty("path", dto.getPath());
			dirArr.add(json);
		}

		JsonObject json = new JsonObject();
		json.addProperty("dirlist", new Gson().toJson(dirArr));
		json.addProperty("parent_seq", parent_seq);

		return new Gson().toJson(json);
	}

	@RequestMapping("renameDirectory")
	@ResponseBody
	public int renameDir(int seq, String rename) {

		int result = -1;

		int parent_seq = fservice.getParentSeqBySeq(seq);
		int duplCheck = fservice.checkDuplDirName(parent_seq, rename);

		if(duplCheck == 0) {
			result = fservice.renameDirectory(seq, rename);

		}

		return result;
	}

	@RequestMapping(value = "uploadFile", produces = "application/text; charset=utf8")
	@ResponseBody
	public String upload(int dir_seq, MultipartFile file) throws Exception {

		MemberDTO member = (MemberDTO) session.getAttribute("loginInfo");

		// int dir_seq = Integer.parseInt(request.getParameter("dir_seq"));

		// 드라이브에 파일 생성
		String name = fservice.uploadFileToDrive(dir_seq, file);
		// DB에 파일 업데이트
		fservice.uploadFile(dir_seq, file, name, member);

		// 디렉토리의 파일 목록 다시 가져오기
		List<FileDTO> fileList = fservice.getFileListByDirSeq(dir_seq);
		JsonArray fileArr = new JsonArray();

		for(FileDTO dto : fileList) {
			JsonObject json = new JsonObject();
			json.addProperty("seq", dto.getSeq());
			json.addProperty("path", dto.getPath());
			json.addProperty("name", dto.getName());
			System.out.println(dto.getText_yn());
			json.addProperty("text_yn", dto.getText_yn());
			fileArr.add(json);
		}

		return new Gson().toJson(fileArr);

	}

	@RequestMapping(value = "checkDirNameDupl", produces = "application/text; charset=utf8")
	@ResponseBody
	public String checkDirNameDupl(int dir_seq, String zip_dir) {
		String result = "";

		// 압축 해제할 디렉토리 이름 중복 체크
		int checkDupl = fservice.checkDuplDirName(dir_seq, zip_dir);
		// 이름 중복일 경우
		if(checkDupl != 0) {
			result = "dupl";
		}
		
		return result;
	}

	@RequestMapping("uploadZip")
	@ResponseBody
	public String uploadZip(int dir_seq, String zip_dir, MultipartFile zip) throws Exception {

		MemberDTO member = (MemberDTO) session.getAttribute("loginInfo");

		String result = "";
		ProjectDTO project = (ProjectDTO) session.getAttribute("projectInfo");
		JsonObject json = new JsonObject();

		// 압축 해제할 디렉토리 이름 중복 체크
		int checkDupl = fservice.checkDuplDirName(dir_seq, zip_dir);
		// 이름 중복일 경우
		if(checkDupl != 0) {
			result = "dupl";
		}

		else {
			fservice.unzip(project.getSeq(), dir_seq, zip, zip_dir, member.getName());
			int zip_dir_seq = fservice.getDirSeqByName(zip_dir, dir_seq);
			json.addProperty("zip_dir_seq", zip_dir_seq);
		}


		json.addProperty("result", result);
		return new Gson().toJson(json);
	}

	@RequestMapping("downloadFile")
	public void download(int seq, HttpServletResponse resp) throws Exception{

		String name = fservice.getFileNameBySeq(seq);
		String rootPath = session.getServletContext().getRealPath("upload/backup");
		String filePath = fservice.getFilePathBySeq(seq);
		String path = rootPath + filePath;

		File target = new File(path);

		DataInputStream dis = new DataInputStream(new FileInputStream(target));
		ServletOutputStream sos = resp.getOutputStream();

		String fileName = new String(name.getBytes("utf8"),"iso-8859-1"); //크롬은 iso-8859-1 인코딩을 사용해서 그걸로 변환해준 것.

		byte[] fileContents = new byte[(int)target.length()];
		dis.readFully(fileContents);

		//response의 디폴트 액션은 SourceCode이다. 그래서 그것을 리셋하여서, response로 지금 보내는 것이 머냐면~ 파일내용을 보내는 String값(application/octet-stream)을 보내고 있다고 알려주는 것. => 소스코드처럼 렌더링하면 안된다고 알려주는 것.
		resp.reset();
		resp.setContentType("application/octet-stream");
		resp.setHeader("Content-disposition", "attachment;filename="+fileName+";");

		sos.write(fileContents);
		sos.flush();

	}

	@RequestMapping(value = "deleteFile", produces = "application/text; charset=utf8")
	@ResponseBody
	public String delete(int dir_seq, int seq) {

		MemberDTO member = (MemberDTO) session.getAttribute("loginInfo");
		fservice.deleteFile(seq, dir_seq, member.getName());

		List<FileDTO> fileList = fservice.getFileListByDirSeq(dir_seq);
		JsonArray fileArr = new JsonArray();

		for(FileDTO dto : fileList) {
			JsonObject json = new JsonObject();
			json.addProperty("seq", dto.getSeq());
			json.addProperty("path", dto.getPath());
			json.addProperty("name", dto.getName());
			fileArr.add(json);
		}

		return new Gson().toJson(fileArr);

	}

	@RequestMapping(value = "readFile", produces = "application/text; charset=utf8")
	@ResponseBody
	public String read(int seq) throws Exception {

		String rootPath = session.getServletContext().getRealPath("upload/backup");
		String filePath = fservice.getFilePathBySeq(seq);
		String path = rootPath + filePath;
		String extension = fservice.getFileExtensionBySeq(seq).substring(1);

		String fileContents = fservice.getFileText(path);

		JsonObject json = new JsonObject();
		json.addProperty("text", fileContents);
		json.addProperty("extension", extension);

		System.out.println(json);
		return new Gson().toJson(json);
	}

	@RequestMapping("renameFile")
	@ResponseBody
	public int renameFile(int seq, String rename) throws Exception {

		int result = -1;

		result = fservice.renameFile(seq, rename);

		return result;
	}

	// DB 'extension' 테이블의 데이터들 저장용 - 임시 함수
	//	@RequestMapping("insertExtensions")
	//	public void insertExtensions() throws Exception {
	//		
	//		String url = "https://highlightjs.org/static/demo/";
	//		Document doc = Jsoup.connect(url).get();
	//		Elements codes = doc.select("pre>code");
	//		
	//		for(Element e : codes) {
	//			String extension = e.attr("class");
	//			fservice.insertExtensions(extension);
	//			Thread.sleep(2000);
	//		}
	//	}

	//	@RequestMapping("project-main")
	//	public String projectMain(Model model) {
	//		
	//		// 프로젝트의 루트 디렉토리 seq 가져옴
	//		int root_seq = fservice.getRootDirSeq(project_seq);
	//		
	//		// DB에서 목록 가져올 때
	//		List<DirectoryDTO> dirList = fservice.getDirList(root_seq);
	//		JsonArray dirArr = new JsonArray();
	//		JsonArray fileArr = new JsonArray();
	//
	//		for(DirectoryDTO dto : dirList) {
	//			JsonObject json = new JsonObject();
	//			json.addProperty("seq", dto.getSeq());
	//			json.addProperty("name", dto.getName());
	//			json.addProperty("path", dto.getPath());
	//			dirArr.add(json);
	//		}
	//		
	//		model.addAttribute("dirlist", new Gson().toJson(dirArr));
	//		return "project-main";
	//	}

}
