Cấu trúc bài exam
Câu 1: xử lý ngày tháng: javaFunctions/baseFunctions/Date_Operator.java
		note: với mục tiêu là tính tổng số ngày giữa 2 chuỗi ngày tháng nhập vào 
		nên mình không xử lý validate với trường hợp closeDate < startDate, 
		tương tự với số tháng nhập vào có thể là số âm.
	
Câu 2:
	Cấu trúc fw apply POM
	- testcases > chứa các test scripts extends BaseTest class chứa các function hỗ trợ
	- actions/commons
			- BasePage: custom selenium keywords
			- BaseTest
			- BrowserList
			- GlobalConstants
			- PageGenerator: khởi tạo các PageObjects
			- VerificationFailures và MethodListener: xử lý failure cases với softAssert
	- actions/pageObjects package - chứa các pageObjects class - xử lý theo từng trang
	- interfaces/pageObjectUIs package - quản lý các locator trên từng page
	- libSelenium + WebDriverManager
	- resource - chứa file xml config test running 
					-> do có sử dụng parameter browser nên buộc phải execute test scripts qua xml file
	
	Do thời gian không đủ nên mình ko implement log/report nào khác.
	- test-output/old/index.html - simple report
	
	Note: Test Script 2: do placeholder hiện tại ko phải default HTML attribute 
	nhưng mình ko chắc đó là chủ ý của đầu bài hay do site thay đổi cấu trúc 
	nên mình implement cả 2 cases: TC_02 và TC_03
	
	Cám ơn vì đã dành thời gian của bạn!