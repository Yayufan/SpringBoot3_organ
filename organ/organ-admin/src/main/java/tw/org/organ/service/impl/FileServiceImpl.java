package tw.org.organ.service.impl;

import tw.org.organ.pojo.DTO.InsertFileDTO;
import tw.org.organ.pojo.entity.File;
import tw.org.organ.convert.FileConvert;
import tw.org.organ.mapper.FileMapper;
import tw.org.organ.service.FileService;
import tw.org.organ.utils.MinioUtil;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 上傳檔案表，用於應用在只有上傳檔案的頁面 服务实现类
 * </p>
 *
 * @author Joey
 * @since 2024-12-27
 */
@Service
@RequiredArgsConstructor
public class FileServiceImpl extends ServiceImpl<FileMapper, File> implements FileService {

	private final FileConvert fileConvert;

	@Value("${minio.bucketName}")
	private String minioBucketName;

	private final MinioUtil minioUtil;
	
	@Override
	public IPage<File> getAllFileByGroup(String group, Page<File> pageInfo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addFile(MultipartFile[] files, InsertFileDTO insertFileDTO) {
		File fileEntity = fileConvert.insertDTOToEntity(insertFileDTO);

		// 檔案存在，處理檔案
		if (files != null && files.length > 0) {
			
			List<String> upload = minioUtil.upload(minioBucketName, insertFileDTO.getGroupType() + "/", files);
			// 基本上只有有一個檔案跟著formData上傳,所以這邊直接寫死,把唯一的url增添進對象中
			String url = upload.get(0);
			// 將bucketName 組裝進url
			url = "/" + minioBucketName + "/" + url;
			// minio完整路徑放路對象中
			fileEntity.setPath(url);

			// 放入資料庫
			baseMapper.insert(fileEntity);

		}

		System.out.println("上傳完成");
	}

	@Override
	public void deleteFile(Long fileId) {
		// TODO Auto-generated method stub
		File fileEntity = baseMapper.selectById(fileId);

	}



}
