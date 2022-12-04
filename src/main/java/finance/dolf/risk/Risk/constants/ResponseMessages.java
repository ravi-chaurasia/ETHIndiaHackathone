package finance.dolf.risk.Risk.constants;

public interface ResponseMessages {
        String SUCCESS = "success";
        String RESOURCE_NOT_FOUND = "Resource is not found";
        String RESOURCE_ALREADY_PRESENT = "Resource is already present";
        String SOME_ERROR_OCCURRED = "Some error occurred, please try again later";
        String MAIL_SENDING_ERROR = "Mail could not not be sent";
        String BAD_REQUEST = "Bad Request";
        String FORBIDDEN = "You are not allowed to perform this action";
        String OLD_PASSWORD_MISMATCH = "Old password is incorrect";
        String INCORRECT_OTP = "OTP does not match";
        String INSUFFICIENT_BALANCE = "There is insufficient balance to perform ";
        String WITHDRAWAL = "Withdrawal";
        String DAILY_WITHDRAWAL_LIMIT_REACHED = "Daily withdrawal limit reached";
        String ADDRESS_NOT_WHITELISTED = "Address is not whilisted";
        String KYC_NOT_COMPLETED = "Kyc is not complete";
        String TOKEN_EXPIRED = "JWT Token has expired";
        String JWT_TOKEN_MISSING = "JWT Token does not begin with Bearer String";
        String INVALID_ASSET_ADDRESS = "Address is invalid";
        String WITHDRAWAL_SUSPENDED = "Withdrawal is suspended";
        String DEPOSIT_NOT_FOUND = "There are no deposits, Please deposit some coins.";
        String UNAUTHORIZED = "You are not authorized";
        String USER_ALREADY_EXISTS = "User already exists";
        String INVALID_REQUEST = "Request is invalid";
        String JWT_TOKEN_EXPIRED = "JWT Token has expired";
        String JWT_SIGNATURE_INVALID = "Invalid JWT token";
        String INVALID_TXN_STATUS = "Transaction status is not valid for given operation";




}
