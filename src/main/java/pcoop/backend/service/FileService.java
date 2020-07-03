package pcoop.backend.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import pcoop.backend.dao.FileDAO;
import pcoop.backend.dto.DirectoryDTO;
import pcoop.backend.dto.FileDTO;

@Service
public class FileService {

	@Autowired
	private HttpSession session;

	@Autowired
	private FileDAO fdao;

	// 드라이브에 디렉토리 생성 후, path 리턴
	public String makeDirToDrive(int parent_seq, String name) {

		String rootDir = session.getServletContext().getRealPath("upload/backup");
		String path = rootDir + fdao.getDirPathBySeq(parent_seq) + "/" + name;
		File dir = new File(path);
		System.out.println(path);

		dir.mkdirs(); //폴더 생성합니다.
		path = path.substring(rootDir.length());

		return path;
	}

	// DB에 디렉토리 insert 후, 디렉토리 seq 리턴
	public int insertDirectory(String path, String name) {
		return fdao.insertDirectory(path, name);
	}

	// 이름으로 디렉토리 seq 검색
	public int getDirSeqByName(String name) {
		return fdao.getDirSeqByName(name);
	}

	// seq로 디렉토리 경로 검색
	public String getDirPathBySeq(int seq) {
		return fdao.getDirPathBySeq(seq);
	}

	// 디렉토리 리스트 가져오기
	public List<DirectoryDTO> getDirList(){
		return fdao.getDirList();
	}

	// 파일 리스트 가져오기
	public List<FileDTO> getFileList(){
		return fdao.getFileList();
	}
	
	// 파일 이름 가져오기
	public String getFileNameBySeq(int seq) {
		return fdao.getFileNameBySeq(seq);
	}
	
	// 파일 경로 가져오기
	public String getFilePathBySeq(int seq) {
		return fdao.getFilePathBySeq(seq);
	}
	
	// 파일 확장자 가져오기
	public String getFileExtensionBySeq(int seq) {
		return fdao.getFileExtensionBySeq(seq);
	}

	// 특정 디렉토리 내 파일 리스트 가져오기 
	public List<FileDTO> getFileListByDirSeq(int dir_seq){
		return fdao.getFileListByDirSeq(dir_seq);
	}
	
	// 디렉토리 삭제
	public void deleteDirectory(int seq, String path) {
		this.deleteDirFromDrive(path);
		this.deleteDirectoryFromDB(path);
		fdao.deleteFileByDir(seq);
	}

	// DB에서 디렉토리 삭제
	public int deleteDirectoryFromDB(String path) {
		return fdao.deleteDirectory(path);
	}

	// 드라이브에서 특정 디렉토리 삭제하기
	public void deleteDirFromDrive(String path) {

		path = session.getServletContext().getRealPath("upload/backup/" + path);
		deleteDirRecursiveFromDrive(path);

	}

	// 하위 디렉토리까지 회귀하여 디렉토리 삭제(from drive)
	public void deleteDirRecursiveFromDrive(String path) {

		File dir = new File(path);
		File[] flist = new File(path).listFiles();

		for (int i = 0; i < flist.length; i++) {

			if(flist[i].isFile()) {
				flist[i].delete();
			}else {
				deleteDirRecursiveFromDrive(flist[i].getPath()); //재귀 함수 호출
			}

			flist[i].delete();
		}

		dir.delete(); //폴더 삭제

	}
	// 드라이브에서 직접 디렉토리 리스트 가져오기
	public List<String> getDirListFromDrive(String strDirPath){

		List<String> list = new ArrayList<>();

		getDirListRecursiveFromDrive(strDirPath, list);
		return list;

	}

	// 하위 디렉토리까지 회귀하여 디렉토리 리스트 추출(from drive)
	public void getDirListRecursiveFromDrive(String strDirPath, List<String> list) {

		File[] flist = new File(strDirPath).listFiles();
		String rootDir = session.getServletContext().getRealPath("upload/backup");

		for(File f : flist) {

			if(!f.getName().equals(".DS_Store")) {

				if(f.isDirectory()) {
					//list.add(f.getPath().substring(rootDir.length()));
					list.add(f.getPath().substring(rootDir.length()));
					System.out.println(f.getPath().substring(rootDir.length()));
					getDirListRecursiveFromDrive(f.getPath(), list);
				}

			}

		}

	}

	// 드라이브에서 직접 파일 리스트 가져오기
	public List<String> getFileListFromDrive(String strDirPath){

		List<String> list = new ArrayList<>();
		getFileListRecursiveFromDrive(strDirPath, list);
		return list;

	}

	// 하위 디렉토리까지 회귀하여 파일 리스트 추출 from drive
	public void getFileListRecursiveFromDrive(String strDirPath, List<String> list) {

		File[] flist = new File(strDirPath).listFiles();
		String rootDir = session.getServletContext().getRealPath("upload/backup");

		for(File f : flist) {

			if(!f.getName().equals(".DS_Store")) {

				if(f.isFile()) {
					list.add(f.getPath().substring(rootDir.length()));
					System.out.println(f.getPath().substring(rootDir.length()));
				}
				else {
					getFileListRecursiveFromDrive(f.getPath(), list);
				}

			}

		}

	}

	// DB에 새로운 파일 추가하고 seq 넘기기
	public void uploadFile(int dir_seq, MultipartFile file, String rename) throws Exception {

		int project_seq = 11;
		String dir_path = fdao.getDirPathBySeq(dir_seq);
		String name = rename;
		String extension = name.substring(name.indexOf('.'));
		String path = dir_path + "/" + name;
		String uploader = "temp";
		String text_yn = "N";
		
		if(fdao.isTextFile(extension) > 0) {
			text_yn = "Y";
		}
		
		fdao.insertFile(project_seq, dir_seq, dir_path, name, extension, path, uploader, text_yn);

	}

	// 파일명 중복 확인 후, rename
	public String renameFile(int dir_seq, MultipartFile file) {

		String name = file.getOriginalFilename();
		int checkDupl = fdao.checkDuplFileName(dir_seq, name);
		String extension = name.substring(name.indexOf('.'));
		String checkName = name;
		name = name.substring(0, name.indexOf('.'));

		int i = 2;

		while(checkDupl > 0) {

			checkName = name + " (" + i + ")" + extension;
			i = i + 1;

			checkDupl = fdao.checkDuplFileName(dir_seq, checkName);

		}

		return checkName;
	}

	// 드라이브에 파일 업로드
	public String uploadFileToDrive(int dir_seq, MultipartFile file) throws Exception {

		// 파일 중복명 확인 후, 수정된 이름 가져오기
		String rename = this.renameFile(dir_seq, file);
		String dirPath = fdao.getDirPathBySeq(dir_seq);
		String path = session.getServletContext().getRealPath("upload/backup/") + dirPath;
		File targetLoc = new File(path + "/" + rename);

		System.out.println(rename);
		if(!file.isEmpty()) {
			file.transferTo(targetLoc);
		}

		return targetLoc.getName();
		
	}
	
	// 파일 지우기
	public void deleteFile(int seq) {
		deleteFileFromDrive(seq);
		deleteFileFromDB(seq);
	}

	// 드라이브에서 파일 지우기
	public void deleteFileFromDrive(int seq) {
		String path = session.getServletContext().getRealPath("upload/backup") + fdao.getFilePathBySeq(seq);
		File file = new File(path);
		file.delete();
	}

	// DB 목록에서 파일 지우기
	public void deleteFileFromDB(int seq) {
		fdao.deleteFile(seq);
	}

	// 파일 텍스트 읽어 오기
	public String getFileText(String path) throws Exception {
		
		File file = new File(path);
	    String fileContents = FileUtils.readFileToString(file, "UTF-8");
	    // fileContents = fileContents.replace("\n", "<br>");
	    return fileContents;
	    
	}
	
	// DB 'extension' 테이블의 데이터들 저장용 - 임시 함수
//	public int insertExtensions(String extension) {
//		return fdao.insertExtensions(extension);
//	}

}
