package pcoop.backend.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FileService {

	@Autowired
	private HttpSession session;

	// 디렉토리 리스트
	public List<String> getDirList(String strDirPath){

		List<String> list = new ArrayList<>();

		getDirListRecursive(strDirPath, list);
		return list;

	}

	// 하위 디렉토리까지 회귀하여 디렉토리 리스트 추출
	public void getDirListRecursive(String strDirPath, List<String> list) {

		File[] flist = new File(strDirPath).listFiles();
		String rootDir = session.getServletContext().getRealPath("upload/backup");

		for(File f : flist) {

			if(!f.getName().equals(".DS_Store")) {

				if(f.isDirectory()) {
					//list.add(f.getPath().substring(rootDir.length()));
					list.add(f.getPath().substring(rootDir.length()));
					System.out.println(f.getPath().substring(rootDir.length()));
					getDirListRecursive(f.getPath(), list);
				}

			}

		}

	}

	// 파일 리스트
	public List<String> getFileList(String strDirPath){

		List<String> list = new ArrayList<>();

		getFileListRecursive(strDirPath, list);
		return list;

	}

	// 하위 디렉토리까지 회귀하여 파일 리스트 추출
	public void getFileListRecursive(String strDirPath, List<String> list) {

		File[] flist = new File(strDirPath).listFiles();
		String rootDir = session.getServletContext().getRealPath("upload/backup");

		for(File f : flist) {

			if(!f.getName().equals(".DS_Store")) {

				if(f.isFile()) {
					list.add(f.getPath().substring(rootDir.length()));
					System.out.println(f.getPath().substring(rootDir.length()));
				}
				else {
					getFileListRecursive(f.getPath(), list);
				}

			}

		}

	}


}
