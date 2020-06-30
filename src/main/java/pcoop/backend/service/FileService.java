package pcoop.backend.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	
	// DB에서 디렉토리 삭제
	public int deleteDirectory(String path) {
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


}
