package tw.org.organ.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import tw.org.organ.convert.OrganDonationConsentConvert;
import tw.org.organ.mapper.OrganDonationConsentMapper;
import tw.org.organ.pojo.DTO.InsertOrganDonationConsentDTO;
import tw.org.organ.pojo.DTO.UpdateOrganDonationConsentDTO;
import tw.org.organ.pojo.entity.OrganDonationConsent;
import tw.org.organ.pojo.excelPojo.OrganDonationConsentExcel;
import tw.org.organ.service.OrganDonationConsentService;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Joey
 * @since 2024-11-13
 */
@Service
@RequiredArgsConstructor
public class OrganDonationConsentServiceImpl extends ServiceImpl<OrganDonationConsentMapper, OrganDonationConsent>
		implements OrganDonationConsentService {

	private final OrganDonationConsentConvert organDonationConsentConvert;
	private String DEFAULT_STATUS = "0";

	@Override
	public OrganDonationConsent getOrganDonationConsent(Long organDonationConsentId) {
		OrganDonationConsent organDonationConsent = baseMapper.selectById(organDonationConsentId);
		return organDonationConsent;
	}

	@Override
	public List<OrganDonationConsent> getAllOrganDonationConsent() {
		List<OrganDonationConsent> organDonationConsentList = baseMapper.selectList(null);
		return organDonationConsentList;
	}

	@Override
	public IPage<OrganDonationConsent> getAllOrganDonationConsent(Page<OrganDonationConsent> page) {
		LambdaQueryWrapper<OrganDonationConsent> organDonationConsentQueryWrapper = new LambdaQueryWrapper<>();
		organDonationConsentQueryWrapper.orderByDesc(OrganDonationConsent::getOrganDonationConsentId);

		Page<OrganDonationConsent> organDonationConsentList = baseMapper.selectPage(page,
				organDonationConsentQueryWrapper);
		return organDonationConsentList;
	}

	@Override
	public IPage<OrganDonationConsent> getAllOrganDonationConsentByStatus(Page<OrganDonationConsent> page,
			String status, String queryText) {

		LambdaQueryWrapper<OrganDonationConsent> organDonationConsentQueryWrapper = new LambdaQueryWrapper<>();

		// 如果 status 不為空字串、空格字串、Null 時才加入篩選條件
		organDonationConsentQueryWrapper.eq(StringUtils.isNotBlank(status), OrganDonationConsent::getStatus, status)
				// 當 queryText 不為空字串、空格字串、Null 時才加入篩選條件
				.and(StringUtils.isNotBlank(queryText),
						wrapper -> wrapper.like(OrganDonationConsent::getName, queryText).or()
								.like(OrganDonationConsent::getIdCard, queryText).or()
								.like(OrganDonationConsent::getContactNumber, queryText).or()
								.like(OrganDonationConsent::getPhoneNumber, queryText))
				.orderByDesc(OrganDonationConsent::getOrganDonationConsentId);

		Page<OrganDonationConsent> organDonationConsentList = baseMapper.selectPage(page,
				organDonationConsentQueryWrapper);
		return organDonationConsentList;

	}

	@Override
	public Long getOrganDonationConsentCount() {
		Long selectCount = baseMapper.selectCount(null);
		return selectCount;
	}

	@Override
	public Long getOrganDonationConsentCount(String status) {
		LambdaQueryWrapper<OrganDonationConsent> organDonationConsentQueryWrapper = new LambdaQueryWrapper<>();
		organDonationConsentQueryWrapper.eq(OrganDonationConsent::getStatus, status);

		Long selectCount = baseMapper.selectCount(organDonationConsentQueryWrapper);
		return selectCount;
	}

	@Override
	public void insertOrganDonationConsent(InsertOrganDonationConsentDTO insertOrganDonationConsentDTO) {
		OrganDonationConsent organDonationConsent = organDonationConsentConvert
				.insertDTOToEntity(insertOrganDonationConsentDTO);
		organDonationConsent.setSignatureDate(LocalDate.now());
		organDonationConsent.setStatus(DEFAULT_STATUS);

		baseMapper.insert(organDonationConsent);

	}

	@Override
	public void updateOrganDonationConsent(UpdateOrganDonationConsentDTO updateOrganDonationConsentDTO) {
		OrganDonationConsent organDonationConsent = organDonationConsentConvert
				.updateDTOToEntity(updateOrganDonationConsentDTO);
		baseMapper.updateById(organDonationConsent);
	}

	@Override
	public void updateOrganDonationConsent(List<UpdateOrganDonationConsentDTO> updateOrganDonationConsentDTOList) {
		for (UpdateOrganDonationConsentDTO updateOrganDonationConsentDTO : updateOrganDonationConsentDTOList) {
			this.updateOrganDonationConsent(updateOrganDonationConsentDTO);
		}

	}

	@Override
	public void deleteOrganDonationConsent(Long organDonationConsentId) {
		baseMapper.deleteById(organDonationConsentId);
	}

	@Override
	public void deleteOrganDonationConsent(List<Long> organDonationConsentIdList) {
		baseMapper.deleteBatchIds(organDonationConsentIdList);
	}

	@Override
	public void downloadExcel(HttpServletResponse response) throws IOException {

		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		response.setCharacterEncoding("utf-8");
		// 这里URLEncoder.encode可以防止中文乱码 ， 和easyexcel没有关系
		String fileName = URLEncoder.encode("測試", "UTF-8").replaceAll("\\+", "%20");
		response.setHeader("Content-disposition", "attachment;filename*=" + fileName + ".xlsx");

		List<OrganDonationConsent> allOrganDonationConsent = this.getAllOrganDonationConsent();
		List<OrganDonationConsentExcel> excelData = allOrganDonationConsent.stream().map(organDonationConsent -> {
			return organDonationConsentConvert.entityToExcel(organDonationConsent);
		}).collect(Collectors.toList());

		EasyExcel.write(response.getOutputStream(), OrganDonationConsentExcel.class).sheet("清單").doWrite(excelData);

	}

	@Override
	public void downloadWord(Long organDonationConsentId, HttpServletResponse response) throws IOException {

		// 1、讀取到範本
		File rootFile = new File(ResourceUtils.getURL("classpath:").getPath()); // 獲取專案的根目錄
		File templateFile = new File(rootFile, "/word_template/organ_template.docx");
		try (XWPFDocument word = new XWPFDocument(new FileInputStream(templateFile))) {
			// 2、根據ID查詢到資料
			OrganDonationConsent organDonationConsent = baseMapper.selectById(organDonationConsentId);
			Map<String, String> params = new HashMap<>();

			// 存放簽署 年月日
			params.put("${signYear}", String.valueOf(organDonationConsent.getSignatureDate().getYear()));
			params.put("${signMonth}", String.valueOf(organDonationConsent.getSignatureDate().getMonthValue()));
			params.put("${signDay}", String.valueOf(organDonationConsent.getSignatureDate().getDayOfMonth()));

			// 存放身分證字號 和 出生日期
			params.put("${idCard}", organDonationConsent.getIdCard());
			params.put("${birYear}", String.valueOf(organDonationConsent.getBirthday().getYear()));
			params.put("${birMonth}", String.valueOf(organDonationConsent.getBirthday().getMonthValue()));
			params.put("${birDay}", String.valueOf(organDonationConsent.getBirthday().getDayOfMonth()));

			// 存放 電話、地址、法定代理人姓名和身分證
			params.put("${phone}", organDonationConsent.getContactNumber());
			params.put("${address}", organDonationConsent.getAddress());
			params.put("${repreName}", organDonationConsent.getLegalRepresentativeName());
			params.put("${repreIdCard}", organDonationConsent.getLegalRepresentativeIdCard());

			// 存放簽署原因、給家人的話
			params.put("${signReason}", organDonationConsent.getReason());
			params.put("${toFamily}", organDonationConsent.getWordToFamily());

			// 判斷拿取捐贈同意卡的意願
			params.put("${hopeCard}", organDonationConsent.getConsentCard().equals("1") ? "■" : "□");
			params.put("${notHopeCard}", organDonationConsent.getConsentCard().equals("-1") ? "■" : "□");

			// 根據器捐項目存放
			String donateOrgans = organDonationConsent.getDonateOrgans();
			List<String> donateOrgansList = Arrays.asList(donateOrgans.split(","));
			System.out.println("器捐項目有: " + donateOrgansList);

			params.put("${all}", donateOrgansList.contains("all") ? "■" : "□");
			params.put("${lung}", donateOrgansList.contains("lung") ? "■" : "□");
			params.put("${pancreas}", donateOrgansList.contains("pancreas") ? "■" : "□");
			params.put("${smallIntestine}", donateOrgansList.contains("smallIntestine") ? "■" : "□");
			params.put("${skin}", donateOrgansList.contains("skin") ? "■" : "□");
			params.put("${heartValve}", donateOrgansList.contains("heartValve") ? "■" : "□");
			params.put("${heart}", donateOrgansList.contains("heart") ? "■" : "□");
			params.put("${liver}", donateOrgansList.contains("liver") ? "■" : "□");
			params.put("${kidney}", donateOrgansList.contains("kidney") ? "■" : "□");
			params.put("${cornea}", donateOrgansList.contains("cornea") ? "■" : "□");
			params.put("${bones}", donateOrgansList.contains("bones") ? "■" : "□");
			params.put("${bloodVessels}", donateOrgansList.contains("bloodVessels") ? "■" : "□");

			// 3、替換資料，處理正文開始
			for (XWPFParagraph paragraph : word.getParagraphs()) {
				List<XWPFRun> runs = paragraph.getRuns();
				StringBuilder placeholderBuilder = new StringBuilder(); // 用於構建佔位符
				boolean isPlaceholder = false; // 是否正在檢測佔位符
				int placeholderStartIndex = -1; // 佔位符開始的 run 索引
				int placeholderEndIndex = -1; // 佔位符結束的 run 索引

				for (int i = 0; i < runs.size(); i++) {
					XWPFRun run = runs.get(i);
					String text = run.getText(0);
					if (text != null) {
						System.out.println("當前text: " + text);

						if (text.contains("$")) {
							// 檢測到佔位符的開始
							isPlaceholder = true;
							placeholderStartIndex = i;
							placeholderBuilder.setLength(0); // 清空佔位符構建器
						}

						if (isPlaceholder) {
							placeholderBuilder.append(text); // 構建佔位符
						}

						if (text.contains("}")) {
							// 檢測到佔位符的結束
							isPlaceholder = false;
							placeholderEndIndex = i;

							// 獲取完整的佔位符
							String placeholder = placeholderBuilder.toString();
							if (params.containsKey(placeholder)) {
								// 替換佔位符
								String replacement = params.get(placeholder);

								// 清空佔位符部分的 runs
								for (int j = placeholderStartIndex; j <= placeholderEndIndex; j++) {
									runs.get(j).setText("", 0);
								}

								// 將替換後的文本設置到第一個 run
								runs.get(placeholderStartIndex).setText(replacement, 0);
							}
						}
					}
				}
			}

			// 3、替換資料，處理正文開始
//			for (XWPFParagraph paragraph : word.getParagraphs()) {
//				List<XWPFRun> runs = paragraph.getRuns();
//				StringBuilder placeholderBuilder = new StringBuilder(); // 用於構建佔位符
//				boolean isPlaceholder = false; // 是否正在檢測佔位符
//				int placeholderStartIndex = -1; // 佔位符開始的 run 索引
//				int placeholderEndIndex = -1; // 佔位符結束的 run 索引
//
//				for (int i = 0; i < runs.size(); i++) {
//					XWPFRun run = runs.get(i);
//					String text = run.getText(0);
//					if (text != null) {
//						System.out.println("當前text: " + text);
//						if (text.contains("$") || text.contains("{")) {
//							// 檢測到佔位符的開始
//							isPlaceholder = true;
//							placeholderStartIndex = i;
//							placeholderBuilder.setLength(0); // 清空佔位符構建器
//						}
//
//						if (isPlaceholder) {
//							placeholderBuilder.append(text); // 構建佔位符
//						}
//
//						if (text.contains("}")) {
//							// 檢測到佔位符的結束
//							isPlaceholder = false;
//							placeholderEndIndex = i;
//
//							// 獲取完整的佔位符
//							String placeholder = placeholderBuilder.toString();
//							if (params.containsKey(placeholder)) {
//								// 替換佔位符
//								String replacement = params.get(placeholder);
//
//								// 清空佔位符部分的 runs
//								for (int j = placeholderStartIndex; j <= placeholderEndIndex; j++) {
//									runs.get(j).setText("", 0);
//								}
//
//								// 將替換後的文本設置到第一個 run
//								runs.get(placeholderStartIndex).setText(replacement, 0);
//							}
//						}
//					}
//				}
//			}

//			List<XWPFParagraph> paragraphs = word.getParagraphs();
//			for (XWPFParagraph paragraph : paragraphs) {
//				List<XWPFRun> runs = paragraph.getRuns();
//				for (XWPFRun run : runs) {
//					String text = run.getText(0);
//					System.out.println("當前text為 " + text);
//					if (text != null) {
//						for (String key : params.keySet()) {
////							System.out.println("當前key為 " + key);
//							if (text.contains(key)) {
////								System.out.println("text匹配到key "+key);
//								run.setText(text.replaceAll(key, params.get(key)), 0);
//							}
//						}
//					}
//				}
//			}

			// 4、匯出word
			String fileName = URLEncoder.encode(organDonationConsent.getName() + "_簽卡.docx", "UTF-8").replaceAll("\\+",
					"%20");

			response.setHeader("content-disposition",
					"attachment;filename=" + new String(fileName.getBytes(), "ISO8859-1"));
			response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			word.write(response.getOutputStream());
		} catch (Exception e) {
			System.out.println("發生錯誤 " + e);
		}

	}

}
