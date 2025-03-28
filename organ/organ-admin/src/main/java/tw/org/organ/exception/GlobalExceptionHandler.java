package tw.org.organ.exception;

import java.util.Map;

import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.SaTokenException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import tw.org.organ.utils.R;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

	// 全局異常處理,當這個異常沒有被特別處理時,一定會走到全局異常,因為Exception範圍最大
	// 執行的方法,如果返回data沒有特別的值,統一泛型用Map即可

	
	/**
	 * 處理自定義-已經存在的單頁面
	 * 
	 * @param exception
	 * @return
	 */
	@ResponseBody
	@ExceptionHandler(value = ExistPageException.class)
	public R<Map<String, Object>> existPageException(ExistPageException exception) {
		String message = exception.getMessage();
		return R.fail(500, message);
	}
	
	/**
	 * 超出Spring 設定單個檔案最大上傳大小, 如需調整請去 application.yml ,
	 * spring.servlet.multipart.max-file-size
	 * 
	 * @param exception
	 * @return
	 */
	@ResponseBody
	@ExceptionHandler(value = MaxUploadSizeExceededException.class)
	public R<Map<String, Object>> maxUploadSizeExceptionHandler(MaxUploadSizeExceededException exception) {

		String message = exception.getMessage();
		log.error("檔案大小超過異常捕獲: ", exception);
		return R.fail(500, message);
	}

	/**
	 * 日期Json轉換異常
	 */
	@ResponseBody
	@ExceptionHandler(value = HttpMessageNotReadableException.class)
	public R<Map<String, Object>> jsonFormatExceptionHandler(HttpMessageNotReadableException exception) {

		Throwable cause = exception.getCause();
		
		log.error("日期Json轉換異常捕獲: ", exception);
		
		String message;

		if (cause instanceof InvalidFormatException) {
			message = " 日期格式錯誤，請確保你的日期格式為 'yyyy-MM-dd HH:mm:ss";
		} else {
			message = " 請求格式錯誤";
		}

		return R.fail(500, message);
	}

	/**
	 * 參數校驗異常MethodArgumentNotValidException
	 * 適用於對 對象 或 複雜類型 的參數進行校驗，比如對 DTO 或 POJO 對象的字段进行校驗
	 */
	@ResponseBody
	@ExceptionHandler(value = MethodArgumentNotValidException.class)
	public R<Map<String, Object>> argumentExceptionHandler(MethodArgumentNotValidException exception) {
		log.error("參數校驗異常捕獲: ", exception);
		String message = exception.getBindingResult().getFieldError().getDefaultMessage();
		return R.fail(500, "參數校驗異常，" + message);
	}

	/**
	 * 參數校驗異常ConstraintViolationException
	 * 適用於對 方法參數 進行校驗，比如對單個參數或簡單類型的參數進行校驗
	 * 
	 */
	@ResponseBody
	@ExceptionHandler(value = ConstraintViolationException.class)
	public R<Map<String, Object>> argumentValidExceptionHandler(ConstraintViolationException exception) {
		log.error("參數校驗異常捕獲: ", exception);
		String message = exception.getLocalizedMessage();
		return R.fail(500, "參數校驗異常，" + message);
	}

	/**
	 * token校驗異常
	 */
	@ExceptionHandler(NotLoginException.class)
	@ResponseBody
	public R<Map<String, Object>> handlerNotLoginException(NotLoginException nle) throws Exception {

		log.error("token校驗異常捕獲: ", nle);
		
		// 打印堆栈，以供调试
		nle.printStackTrace();

		// 判断登入場景值，定制化異常信息
		String message = "";
		if (nle.getType().equals(NotLoginException.NOT_TOKEN)) {
			message = "未能讀取到 token,請重新登入";
		} else if (nle.getType().equals(NotLoginException.INVALID_TOKEN)) {
			message = "token 無效,請重新登入";
		} else if (nle.getType().equals(NotLoginException.TOKEN_TIMEOUT)) {
			// 使用Redis的話並不會出現這個狀態,只有使用JWT時才會有,因為當token過期時會直接從redis消失
			message = "token 已過期,請重新登入";
		} else if (nle.getType().equals(NotLoginException.BE_REPLACED)) {
			message = "token 已被註銷下線";
		} else if (nle.getType().equals(NotLoginException.KICK_OUT)) {
			message = "token 已被踢下線,請於24小時後再嘗試登入";
		} else if (nle.getType().equals(NotLoginException.TOKEN_FREEZE)) {
			// 這邊通常只適用active-timeout 有設置時間, 且超過可允許的待機時間時,才會報token凍結異常
			message = "token 已被凍結";
		} else if (nle.getType().equals(NotLoginException.NO_PREFIX)) {
			message = "未按照指定前缀提交 token";
		} else {
			message = "當前會話未登入";
		}

		// 返回给前端
		return R.fail(401, message);
	}

	// saToken異常處理
	@ExceptionHandler(SaTokenException.class)
	@ResponseBody
	public R<Map<String, Object>> error(SaTokenException e) {
		
		log.error("saToken異常捕獲: ", e);
		e.printStackTrace();
		// 根据不同异常细分状态码返回不同的提示
		// 前端沒回傳token的時候
		if (e.getCode() == 11001 || e.getCode() == 11011) {
			return R.fail(401, "未能讀取到有效Token,請重新登入");
		}

		// 最常見應該是這個
		if (e.getCode() == 11012) {
			return R.fail(401, "Token無效,請重新登入");
		}
		// 前端回傳的token已經過期的時候,因為放在localStorage所以有機會過期
		if (e.getCode() == 11013) {
			return R.fail(401, "Token已過期,請重新登入");
		}

		// 更多 code 码判断 ...

		// 默认的提示
		return R.fail(e.getCode(), e.getMessage());
	}

	// 特定異常處理,處理數據為0異常
	@ExceptionHandler(ArithmeticException.class)
	@ResponseBody
	public R<Map<String, Object>> error(ArithmeticException e) {
		log.error("數值計算異常捕獲,數字有0值參與計算: ", e);
		e.printStackTrace();
		return R.fail("計算異常");
	}

	/**
	 * 通用異常處理
	 */
	@ExceptionHandler(Exception.class)
	@ResponseBody
	public R<Map<String, Object>> error(Exception e) {
		log.error("全局異常捕獲: ", e);
		e.printStackTrace();
		return R.fail("功能異常，請稍後重試...");
	}

}
