package pcoop.backend.controller;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import pcoop.backend.dto.DirectoryDTO;
import pcoop.backend.dto.FileDTO;
import pcoop.backend.service.FileService;

@Controller
public class FileController {

	@Autowired
	HttpSession session;
	@Autowired
	FileService fservice;

	@RequestMapping("fileList")
	public String fileList(Model model) {

		String project_name = "temp";

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
		List<DirectoryDTO> dirList = fservice.getDirList();
		List<FileDTO> fileList = fservice.getFileList();
		JsonArray dirArr = new JsonArray();
		JsonArray fileArr = new JsonArray();

		for(DirectoryDTO dto : dirList) {
			JsonObject json = new JsonObject();
			json.addProperty("seq", dto.getSeq());
			json.addProperty("path", dto.getPath());
			dirArr.add(json);
		}

		for(FileDTO dto : fileList) {
			JsonObject json = new JsonObject();
			json.addProperty("seq", dto.getSeq());
			json.addProperty("path", dto.getPath());
			fileArr.add(json);
		}

		model.addAttribute("dirlist", new Gson().toJson(dirArr));
		model.addAttribute("filelist", new Gson().toJson(fileArr));
		return "backup/fileList";
	}

	@RequestMapping("getFileList")
	@ResponseBody
	public String getFileList(int dir_seq) {

		List<FileDTO> fileList = fservice.getFileListByDirSeq(dir_seq);
		JsonArray fileArr = new JsonArray();

		for(FileDTO dto : fileList) {
			JsonObject json = new JsonObject();
			json.addProperty("seq", dto.getSeq());
			json.addProperty("path", dto.getPath());
			json.addProperty("name", dto.getName());
			System.out.println("name : " + dto.getName());
			fileArr.add(json);
		}

		return new Gson().toJson(fileArr);
	}

	@RequestMapping("addDirectory")
	@ResponseBody
	public int addDirectory(int parent_seq, String name) {

		// 드라이브에 디렉토리 생성
		String path = fservice.makeDirToDrive(parent_seq, name);
		// DB에 디렉토리 insert
		fservice.insertDirectory(path, name);
		// 새로 생성된 디렉토리 seq 얻기
		int seq = fservice.getDirSeqByName(name);

		return seq;
	}

	@RequestMapping("deleteDirectory")
	@ResponseBody
	public String deleteDirectory(int seq) {

		String path = fservice.getDirPathBySeq(seq);

		// 드라이브에서 디렉토리 삭제
		fservice.deleteDirFromDrive(path);
		// DB에서 디렉토리 delete
		fservice.deleteDirectory(path);

		// 업데이트된 리스트 보내기
		List<DirectoryDTO> dirList = fservice.getDirList();
		List<FileDTO> fileList = fservice.getFileList();

		JsonArray dirArr = new JsonArray();
		JsonArray fileArr = new JsonArray();

		for(DirectoryDTO dto : dirList) {
			JsonObject json = new JsonObject();
			json.addProperty("seq", dto.getSeq());
			json.addProperty("path", dto.getPath());
			dirArr.add(json);
		}

		for(FileDTO dto : fileList) {
			JsonObject json = new JsonObject();
			json.addProperty("seq", dto.getSeq());
			json.addProperty("path", dto.getPath());
			fileArr.add(json);
		}

		JsonObject json = new JsonObject();
		json.addProperty("dirlist", new Gson().toJson(dirArr));
		json.addProperty("filelist", new Gson().toJson(fileArr));
		return new Gson().toJson(json);
	}

	@RequestMapping("uploadFile")
	@ResponseBody
	public String upload(MultipartFile file, HttpServletRequest request) throws Exception {

		int dir_seq = Integer.parseInt(request.getParameter("dir_seq"));

		// 드라이브에 파일 생성
		String name = fservice.uploadFileToDrive(dir_seq, file);
		// DB에 파일 업데이트
		fservice.uploadFile(dir_seq, file, name);

		// 디렉토리의 파일 목록 다시 가져오기
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

	@RequestMapping("deleteFile")
	@ResponseBody
	public String delete(int dir_seq, int seq) {

		fservice.deleteFileFromDrive(seq);

		List<FileDTO> fileList = fservice.getFileListByDirSeq(dir_seq);
		JsonArray fileArr = new JsonArray();

		for(FileDTO dto : fileList) {
			JsonObject json = new JsonObject();
			json.addProperty("seq", dto.getSeq());
			json.addProperty("path", dto.getPath());
			json.addProperty("name", dto.getName());
			System.out.println("name : " + dto.getName());
			fileArr.add(json);
		}

		return new Gson().toJson(fileArr);

	}

}
