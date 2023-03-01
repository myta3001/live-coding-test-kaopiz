package commons;

import java.util.List;

import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.internal.Utils;

public class MethodListener implements IInvokedMethodListener {

	@Override
	public void beforeInvocation(IInvokedMethod method, ITestResult result) {
		
	}

	@Override
	public void afterInvocation(IInvokedMethod method, ITestResult result) {
		Reporter.setCurrentTestResult(result);
		if (method.isTestMethod()) {
			VerificationFailures allFailures = VerificationFailures.getFailures();

			if (result.getThrowable() != null) {
				allFailures.addFailureForTest(result, result.getThrowable());
			}

			List<Throwable> failures = allFailures.getFailuresForTest(result);
			int size = failures.size() - 1;

			if (size > 0) {
				result.setStatus(ITestResult.FAILURE);
				if (size == 1) {
					result.setThrowable(failures.get(0));
				} else {
					StringBuffer message = new StringBuffer();
					for (int failure = 0; failure < size -1; failure++) {
						message.append("\nFailure ").append(failure + 1).append(" of ").append(size).append("\n");
						message.append(Utils.longStackTrace(failures.get(failure), false)).append("\n");
					}
					Throwable last = failures.get(size - 1);
					message.append("Failure ").append(size).append(" of ").append(size).append("\n");
					message.append(last.toString());
					
					Throwable merged = new Throwable(message.toString());
					merged.setStackTrace(last.getStackTrace());
					
					result.setThrowable(new Exception("There are " + size + " failures.",merged));
				}
			}
		}
	}
}
