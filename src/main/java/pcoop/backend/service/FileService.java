package pcoop.backend.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.servlet.http.HttpSession;

import org.apache.commons.compress.archivers.zip.ZipFile;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
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

	public int createProjectBackup(int seq, String name) {
		this.createProjectBackuptoDrive(name);
		return this.createProjectBackuptoDB(seq, name);
	}

	public void createProjectBackuptoDrive(String name) {
		String rootDir = session.getServletContext().getRealPath("upload/backup");
		String path = rootDir + "/" + name;
		File root_dir = new File(path);
		root_dir.mkdir();
	}

	public int createProjectBackuptoDB(int seq, String name) {
		String path = "/" + name;
		return fdao.insertRootDirectory(seq, name, path);
	}

	public int checkDuplDirName(int parent_seq, String name) {
		return fdao.checkDuplDirName(parent_seq, name);
	}

	// 프로젝트의 루트 디렉토리 seq 검색
	public int getRootDirSeq(int project_seq) {
		return fdao.getRootDirSeq(project_seq);
	}

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

	// DB에 디렉토리 insert
	public int insertDirectory(String path, String name, int parent_seq) {
		return fdao.insertDirectory(path, name, parent_seq);
	}

	// 이름으로 디렉토리 seq 검색
	public int getDirSeqByName(String name, int parent_seq) {
		return fdao.getDirSeqByName(name, parent_seq);
	}

	// seq로 디렉토리 경로 검색
	public String getDirPathBySeq(int seq) {
		return fdao.getDirPathBySeq(seq);
	}

	// seq로 디렉토리의 parent_seq 검색
	public int getParentSeqBySeq(int seq) {
		return fdao.getParentSeqBySeq(seq);
	}

	// 디렉토리의 하위 디렉토리 가져오기
	public List<DirectoryDTO> getDirList(int root_seq){
		return fdao.getDirList(root_seq);
	}

	// 디렉토리의 하위 디렉토리 리스트 검색
	public List<DirectoryDTO> getDirListByDirSeq(int seq){
		return getDirListByDirSeq(seq);
	}

	// 디렉토리 이름 변경 
	public int renameDirectory(int seq, String rename) {

		int result = -1;

		String path = fdao.getDirPathBySeq(seq);
		String repath = path.substring(0, path.lastIndexOf('/') + 1);
		repath = repath + rename;
		this.renameDirectoryFromDrive(seq, rename, repath);
		result = this.renameDirectoryFromDB(seq, rename, repath);
		this.renameFilesByDirSeq(seq, repath);

		return result;

	}

	// 디렉토리 이름 변경 from Drive
	public void renameDirectoryFromDrive(int seq, String rename, String repath) {

		String root_path = session.getServletContext().getRealPath("upload/backup");
		String real_path = root_path + fdao.getDirPathBySeq(seq);

		File ori_dir = new File(real_path);
		File new_dir = new File(root_path + repath);
		ori_dir.renameTo(new_dir);

	}

	// 디렉토리 이름 변경 from DB
	public int renameDirectoryFromDB(int seq, String rename, String repath) {
		return fdao.renameDirectory(seq, rename, repath);
	}

	// 디렉토리 이름 변경 시, 파일 path도 변경 from DB
	public int renameFilesByDirSeq(int dir_seq, String repath) {

		List<FileDTO> list = this.getFileListByDirSeq(dir_seq);

		for(FileDTO f : list) {
			String frepath = repath + "/" + f.getName();
			fdao.repathFileByDirSeq(f.getSeq(), repath, frepath);
		}
		return 1;
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
		String dir_path = this.getDirPathBySeq(seq);
		this.deleteDirFromDrive(path);
		this.deleteDirectoryFromDB(path);
		fdao.deleteFilesByDirPath(dir_path);
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
		String extension = null;
		String uploader = "temp";
		String text_yn = "N";

		if(name.contains(".")) {
			extension = name.substring(name.indexOf('.') + 1);
			if(fdao.isTextFile(extension) > 0) {
				text_yn = "Y";
			}
		}

		String path = dir_path + "/" + name;

		fdao.insertFile(project_seq, dir_seq, dir_path, name, extension, path, uploader, text_yn);

	}

	public void uploadFile(int dir_seq, File file, String rename) throws Exception {

		int project_seq = 11;
		String dir_path = fdao.getDirPathBySeq(dir_seq);
		String name = rename;
		String extension = null;
		String uploader = "temp";
		String text_yn = "N";

		if(name.contains(".")) {
			extension = name.substring(name.lastIndexOf('.') + 1);
			if(fdao.isTextFile(extension) > 0) {
				text_yn = "Y";
			}
		}

		String path = dir_path + "/" + name;
		fdao.insertFile(project_seq, dir_seq, dir_path, name, extension, path, uploader, text_yn);

	}

	// 파일 업로드 - .zip - 압축 해제
	public void unzip(int dir_seq, MultipartFile zip, String zip_dir) throws Exception {

		String path = this.makeDirToDrive(dir_seq, zip_dir);
		this.insertDirectory(path, zip_dir, dir_seq);
		int zip_dir_seq = this.getDirSeqByName(zip_dir, dir_seq);

		// 압축 해제하기 위해 생성하는 디렉토리
		String dirPath = fdao.getDirPathBySeq(zip_dir_seq);
		path = session.getServletContext().getRealPath("upload/backup") + dirPath;
		File zipFile = new File(path + "/" + zip.getOriginalFilename());

		if(!zip.isEmpty()) {
			zip.transferTo(zipFile);
		}

		Charset CP866 = Charset.forName("CP866");

		ZipFile zf = new ZipFile(zipFile, CP866.name());
		Enumeration e = zf.getEntries();

		FileInputStream fis = null;
		ZipInputStream zis = null;
		ZipEntry zipentry = null;

		// 파일 스트림
		fis = new FileInputStream(zipFile);

		// Zip 파일 스트림
		zis = new ZipInputStream(fis, Charset.forName("Cp437"));

		// entry가 없을 때까지 뽑기
		// (zipentry = zis.getNextEntry()) != null
		while ((zipentry = zis.getNextEntry()) != null) {

			String filename = zipentry.getName();
			File file = new File(path, filename);

			// System.out.println(filename);
			// entry가 폴더면 폴더 생성
			if (zipentry.isDirectory()) {

				// /temp/project/BoardProject 2
				// src/test/java/
				file.mkdirs();

				int countDir = StringUtils.countMatches(filename, "/");

				String parent_path;
				String name;

				// parent_path 설정
				// 하위 폴더 없을 경우
				if(countDir == 1) {
					parent_path = dirPath;
					name = filename.substring(0, filename.length() - 1);
				}
				// 하위 폴더 있을 경우
				else{
					parent_path = dirPath + "/" + filename.substring(0, filename.lastIndexOf("/", filename.length() - 2));
					name = filename.substring(filename.lastIndexOf("/", filename.length() - 2) + 1, filename.lastIndexOf("/"));
				}

				int parent_seq = fdao.getDirSeqByPath(parent_path);
				fdao.insertDirectory(dirPath + "/" + filename.substring(0, filename.length() - 1), name, parent_seq);

			} else {

				// 파일이면 파일 만들기
				// 디렉토리 확인
				File parentDir = new File(file.getParent());
				String parent_path = parentDir.getPath();
				parent_path = parent_path.substring(session.getServletContext().getRealPath("upload/backup").length());
				int directory_seq = fdao.getDirSeqByPath(parent_path.replace("\\", "/"));

				// 디렉토리가 없으면 생성
				if (!parentDir.exists()) {
					parentDir.mkdirs();
				}

				// 파일 스트림 선언
				FileOutputStream fos = new FileOutputStream(file);

				byte[] buffer = new byte[256];
				int size = 0;
				// Zip 스트림으로부터 byte 뽑기
				while ((size = zis.read(buffer)) > 0) {
					// byte로 파일 만들기
					fos.write(buffer, 0, size);
				}

				this.uploadFile(directory_seq, file, file.getName());

			}


		}

		fis.close();
		zipFile.delete();

	}

	// 파일명 중복 확인 후, rename
	public String renameDuplFile(int dir_seq, MultipartFile file) {

		String name = file.getOriginalFilename();

		if(!name.contains(".")) {
			return name;
		}

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
		String rename = this.renameDuplFile(dir_seq, file);
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

	// 파일 이름 변경
	public int renameFile(int seq, String rename) {

		String extension = this.getFileExtensionBySeq(seq);

		System.out.println(extension);
		if(extension != null)
			rename = rename + "." + extension;

		this.renameFileFromDrive(seq, rename);
		return this.renameFileFromDB(seq, rename);

	}

	// 파일 이름 변경 from Drive
	public void renameFileFromDrive(int seq, String rename) {

		String ori_path = this.getFilePathBySeq(seq);
		String root_path = session.getServletContext().getRealPath("upload/backup");
		ori_path = root_path + ori_path;
		String new_path = ori_path.substring(0, ori_path.lastIndexOf('/') + 1);

		File ori_dir = new File(ori_path);
		File new_dir = new File(new_path + "/" + rename);
		ori_dir.renameTo(new_dir);
	}

	public int renameFileFromDB(int seq, String rename) {

		String path = this.getFilePathBySeq(seq);
		path = path.substring(0, path.lastIndexOf('/') + 1);
		String repath = path + rename;

		return fdao.renameFile(seq, rename, repath);
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
