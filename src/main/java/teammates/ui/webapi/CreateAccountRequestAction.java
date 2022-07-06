package teammates.ui.webapi;

import teammates.common.datatransfer.attributes.AccountRequestAttributes;
import teammates.common.exception.EntityAlreadyExistsException;
import teammates.common.exception.InvalidParametersException;
import teammates.common.util.EmailWrapper;
import teammates.ui.output.JoinLinkData;
import teammates.ui.request.AccountCreateRequest;
import teammates.ui.request.InvalidHttpRequestBodyException;

/**
 * Creates a new account request.
 */
class CreateAccountRequestAction extends Action {

    @Override
    AuthType getMinAuthLevel() {
        return AuthType.PUBLIC;
    }

    @Override
    void checkSpecificAccessControl() throws UnauthorizedAccessException {
        // TODO: check based on intent
    }

    @Override
    public JsonResult execute() throws InvalidHttpRequestBodyException, InvalidOperationException {
        AccountCreateRequest createRequest = getAndValidateRequestBody(AccountCreateRequest.class);

        String instructorName = createRequest.getInstructorName().trim();
        String instructorInstitute = createRequest.getInstructorInstitute().trim();
        String instructorEmail = createRequest.getInstructorEmail().trim();

        AccountRequestAttributes accountRequestToCreate = AccountRequestAttributes
                .builder(instructorEmail, instructorInstitute, instructorName)
                .build();
        AccountRequestAttributes accountRequestAttributes;

        try {
            accountRequestAttributes = logic.createAccountRequest(accountRequestToCreate);
            // only schedule for search indexing if account request created successfully
            taskQueuer.scheduleAccountRequestForSearchIndexing(instructorEmail, instructorInstitute);
        } catch (InvalidParametersException ipe) {
            throw new InvalidHttpRequestBodyException(ipe);
        } catch (EntityAlreadyExistsException eaee) {
            // Use existing account request
            accountRequestAttributes = logic.getAccountRequest(instructorEmail, instructorInstitute);
        }

        assert accountRequestAttributes != null;

        if (accountRequestAttributes.getRegisteredAt() != null) {
            throw new InvalidOperationException("Cannot create account request as instructor has already registered.");
        }

        String joinLink = accountRequestAttributes.getRegistrationUrl();

        EmailWrapper email = emailGenerator.generateNewInstructorAccountJoinEmail(
                instructorEmail, instructorName, joinLink);
        emailSender.sendEmail(email);

        JoinLinkData output = new JoinLinkData(joinLink);
        return new JsonResult(output);
    }

}
