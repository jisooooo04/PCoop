package pcoop.backend.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import pcoop.backend.dto.DirectoryDTO;
import pcoop.backend.dto.FileDTO;

@Repository
public class FileDAO {

	@Autowired
	private JdbcTemplate jdbc;
	@Autowired
	private SqlSessionTemplate mybatis;

	// 프로젝트 생성 시, Root 디렉토리 생성함
	public int insertRootDirectory(int seq, String name, String path) {

		HashMap<String, Object> values = new HashMap<>();
		values.put("seq", seq);
		values.put("name", name);
		values.put("path", path);

		return mybatis.insert("Backup.insertRootDir", values);

	}

	// 프로젝트의 루트 디렉토리 검색
	public int getRootDirSeq(int project_seq) {
		return mybatis.selectOne("Backup.getRootDirSeq", project_seq);
	}

	// 이름으로 디렉토리 seq 검색
	public int getDirSeqByName(String name, int parent_seq) {
		HashMap<String, Object> values = new HashMap<>();
		values.put("name", name);
		values.put("parent_seq", parent_seq);
		return mybatis.selectOne("Backup.getDirSeqByName", values);
	}

	// seq로 디렉토리 경로 검색
	public String getDirPathBySeq(int seq) {
		return mybatis.selectOne("Backup.getDirPathBySeq", seq);
	}

	// seq로 디렉토리의 parent_seq 검색
	public int getParentSeqBySeq(int seq) {
		return mybatis.selectOne("Backup.getParentSeqBySeq", seq);
	}

	// path로 디렉토리의 seq 검색
	public int getDirSeqByPath(String path) {
		return mybatis.selectOne("Backup.getDirSeqByPath", path);
	}

	// 디렉토리 중복 확인
	public int checkDuplDirName(int parent_seq, String name) {
		HashMap<String, Object> values = new HashMap<>();
		values.put("parent_seq", parent_seq);
		values.put("name", name);
		return mybatis.selectOne("Backup.checkDuplDirName", values);
	}

	// 디렉토리나 파일이 삭제됐는지 로그 체크
	public int checkDeleteLog(String type, int seq) {

		HashMap<String, Object> values = new HashMap<>();
		values.put("type", type);
		values.put("seq", seq);

		// 삭제됐는지 확인
		// 삭제됐으면 1, 안 됐으면 0 리턴
		return mybatis.selectOne("Backup.isDeleted", values);

	}
	
	// 삭제된 디렉토리나 파일의 상위 디렉토리 리턴
	public int seleteParentSeqByDeleteLog(String type, int seq) {
		HashMap<String, Object> values = new HashMap<>();
		values.put("type", type);
		values.put("seq", seq);
		return mybatis.selectOne("Backup.selectParentSeqByDeleteLog", values);
	}

	// 디렉토리 insert
	public int insertDirectory(String path, String name, int project_seq, int parent_seq) {
		HashMap<String, Object> values = new HashMap<>();
		values.put("project_seq", project_seq);
		values.put("parent_seq", parent_seq);
		values.put("name", name);
		values.put("path", path);
		return mybatis.insert("Backup.insertDirectory", values);
	}

	// 디렉토리 add log
	public int insertAddDirLog(int seq, int parent_seq, String member_name) {
		HashMap<String, Object> values = new HashMap<>();
		values.put("seq", seq);
		values.put("parent_seq", parent_seq);
		values.put("member_name", member_name);
		return mybatis.insert("insertAddDirLog", values);
	}

	// 디렉토리가 존재하는지 확인
	public int dirExists(String path) {
		return mybatis.selectOne("Backup.dirExists", path);
	}

	// 디렉토리 delete
	public int deleteDirectory(String path) {
		return mybatis.delete("Backup.deleteDirectory", path);
	}

	// 디렉토리 delete log
	public int insertDelDirLog(int seq, int parent_seq, String member_name) {
		HashMap<String, Object> values = new HashMap<>();
		values.put("seq", seq);
		values.put("parent_seq", parent_seq);
		values.put("member_name", member_name);
		return mybatis.insert("insertDelDirLog", values);
	}

	// 디렉토리 이름 및 path 변경
	public int renameDirectory(int seq, String rename, String repath) {
		HashMap<String, Object> values = new HashMap<>();
		values.put("name", rename);
		values.put("path", repath);
		values.put("seq", seq);
		return mybatis.update("Backup.renameDirectory", values);
	}

	// 디렉토리 이름 변경 시, 하위 파일들 path 변경
	public int repathFileByDirSeq(int seq, String repath, String frepath) {
		HashMap<String, Object> values = new HashMap<>();
		values.put("repath", repath);
		values.put("frepath", frepath);
		values.put("seq", seq);
		return mybatis.update("Backup.repathFileByDirSeq", values);
	}

	// 디렉토리의 하위 디렉토리 리스트
	public List<DirectoryDTO> getDirList(int root_seq){
		return mybatis.selectList("Backup.getDirList", root_seq);
	}

	// 디렉토리의 모든 하위 디렉토리 리스트
	public List<DirectoryDTO> getDirListByPath(String path){
		return mybatis.selectList("Backup.getDirListByPath", path + "%");
	}

	// 파일 seq 가져오기
	public int getFileSeq(int directory_seq, String name) {
		HashMap<String, Object> values = new HashMap<>();
		values.put("directory_seq", directory_seq);
		values.put("name", name);
		System.out.println(directory_seq + " : " + name);
		return mybatis.selectOne("Backup.getFileSeqByName", values);
	}

	// 디렉토리 모든 하위 파일들 가져오기
	public List<FileDTO> getFileListByPath(String path){
		return mybatis.selectList("Backup.getFileListByPath", path + "%");	
	}

	// 파일 이름 가져오기
	public String getFileNameBySeq(int seq) {
		return mybatis.selectOne("Backup.getFileNameBySeq", seq);
	}

	// 파일 경로 가져오기
	public String getFilePathBySeq(int seq) {
		return mybatis.selectOne("Backup.getFilePathBySeq", seq);
	}

	// 파일의 확장자가 텍스트인지 체크
	public int isTextFile(String extension) {
		return mybatis.selectOne("Backup.isTextFile", extension);
	}

	// 파일 확장자 가져오기
	public String getFileExtensionBySeq(int seq) {
		return mybatis.selectOne("Backup.getFileExtensionBySeq", seq);
	}

	// 같은 디렉토리 내 파일명 중복 확인
	public int checkDuplFileName(int directory_seq, String name) {
		HashMap<String, Object> values = new HashMap<>();
		values.put("directory_seq", directory_seq);
		values.put("name", name);
		return mybatis.selectOne("Backup.checkDuplFileName", values);
	}

	// 특정 디렉토리 내 파일 리스트
	public List<FileDTO> getFileListByDirSeq(int dir_seq){
		return mybatis.selectList("Backup.getFileListByDirSeq", dir_seq);

	}

	// 특정 디렉토리 내 모든 디렉토리 지우기
	public int deleteDirsByDirPath(String dir_path) {
		return mybatis.delete("Backup.deleteDirsByDirPath", dir_path + "%");
	}

	// 특정 디렉토리 내 모든 파일 지우기
	public int deleteFilesByDirPath(String dir_path) {
		return mybatis.delete("Backup.deleteFilesByDirPath", dir_path + "%");
	}

	// 전체 파일 리스트
	public List<FileDTO> getFileList(){
		return mybatis.selectList("Backup.getFileList");
	}

	// 파일 생성
	public int insertFile(int project_seq, int directory_seq, String directory_path,
			String name, String extension, String path, String uploader, String text_yn){
		HashMap<String, Object> values = new HashMap<>();
		values.put("project_seq", project_seq);
		values.put("directory_seq", directory_seq);
		values.put("directory_path", directory_path);
		values.put("name", name);
		values.put("extension", extension);
		values.put("path", path);
		values.put("uploader", uploader);
		values.put("text_yn", text_yn);
		return mybatis.insert("Backup.insertFile", values);
	}

	// 파일 add log
	public int insertAddFileLog(int seq, int dir_seq, String member_name) {
		HashMap<String, Object> values = new HashMap<>();
		values.put("seq", seq);
		values.put("dir_seq", dir_seq);
		values.put("member_name", member_name);
		return mybatis.insert("insertAddFileLog", values);
	}

	// 파일 삭제
	public int deleteFile(int seq) {
		return mybatis.delete("Backup.deleteFile", seq);
	}

	public int insertDelFileLog(int seq, int dir_seq, String member_name) {
		HashMap<String, Object> values = new HashMap<>();
		values.put("seq", seq);
		values.put("dir_seq", dir_seq);
		values.put("member_name", member_name);
		return mybatis.insert("Backup.insertDelFileLog", values);
	}

	// 파일 이름 변경
	public int renameFile(int seq, String rename, String repath) {
		HashMap<String, Object> values = new HashMap<>();
		values.put("name", rename);
		values.put("path", repath);
		values.put("seq", seq);
		return mybatis.update("Backup.renameFile", values);
	}

	// DB 'extension' 테이블의 데이터들 저장용 - 임시 함수
	//	public int insertExtensions(String extension) {
	//		String sql = "insert into extension values(extension_seq.nextval, ?)";
	//		return jdbc.update(sql, extension);
	//	}

}
