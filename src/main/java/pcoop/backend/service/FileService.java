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
	
	public String getDirPath(int seq) {
		return fdao.getDirPath(seq);
	}
	
	// DB에서 디렉토리 리스트 가져오기
	public List<DirectoryDTO> getDirList(){
		
		return fdao.getDirList();
		
	}
	
	// DB에서 파일 리스트 가져오기
	public List<FileDTO> getFileList(){
		return fdao.getFileList();
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
