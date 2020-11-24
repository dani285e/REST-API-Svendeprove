package com.api.rest.spring.handlers;

import com.api.rest.spring.Entity.Dto.UserDto;
import com.api.rest.spring.Entity.Enum.Role;
import com.api.rest.spring.Entity.User;
import com.api.rest.spring.Entity.builders.UserBuilder;
import com.api.rest.spring.WebApiHelper;
import com.api.rest.spring.handlers.exceptions.AuthorizationException;
import com.api.rest.spring.handlers.exceptions.ValidationException;
import com.api.rest.spring.repository.UserRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.util.Objects;
import java.util.UUID;

public class UserHandler {

    //TODO make general userValidation method

    public UserHandler(UserRepository userRepository) {
        this.userRepository = Objects.requireNonNull(userRepository);
    }

    private UserRepository userRepository;

    public User findUserById(Integer id) throws DataAccessException {
        //TODO Exception handling
        try {
            return userRepository.findUserById(id);
        } catch (DataAccessException e) {
            e.printStackTrace();
            //TODO Ask Jonas what to return, return stack or return null?
            return null;
        }
    }

    public User findUserByUsername (String username) throws DataAccessException {
        //TODO Exception handling
        try {
            return userRepository.findUserByUsername(username);
        } catch (DataAccessException e) {
            e.printStackTrace();
            //TODO Ask Jonas what to return, return stack or return null?
            return null;
        }
    }

    private void validateUserForCreating(String username, String role, String password, String email) throws ValidationException {
        if (username == null || username.isEmpty())
            throw new ValidationException("Username is null or empty");

        if (userRepository.findUserByUsername(username) != null)
            throw new ValidationException("Username <" + username + "> is already used");

        if (role == null || Role.getRoleByName(role) == null)
            throw new ValidationException("Role <" + role + "> does not exist");

        Role foundRole = Role.getRoleByName(role);

        if (foundRole == Role.SUPER_ADMIN)
            throw new ValidationException("Role <" + foundRole + "> is not allowed");

        if (password == null || password.isEmpty())
            throw new ValidationException("Password is null or empty");

        if (password == username || password == email)
            throw new ValidationException("Password can't be the same as username or password");

        if (email == null || email.isEmpty())
            throw new ValidationException("Email is null or empty");



        //TODO ? mail validation ?

    }

    public void createUser(String username, String role, String password, String email) throws ValidationException {
        //TODO add hashing to method
        validateUserForCreating(username, role, password, email);
        String salt = BCrypt.gensalt();
        String hashPassword = BCrypt.hashpw(password, salt);

        UserBuilder userBuilder = UserBuilder.aUserBuilder();

        User build = userBuilder
                .withUsername(username)
                .withRole(Role.getRoleByName(role))
                .withSalt(salt)
                .withPassword(hashPassword)
                .withUserStatus(WebApiHelper.ACTIVATED_USER)
                .withEmail(email)
                .build();

        userRepository.save(build);
    }

    /**
     *
     * @param updateUserId
     * @param requestingUserId
     * @param userDto
     * @throws Exception
     */
    public void updateUser(Integer updateUserId, Integer requestingUserId, UserDto userDto) throws Exception {

        User updateUser = findUserById(updateUserId);
        User requestingUser = findUserById(requestingUserId);
        ValidateUser(updateUser, requestingUser, userDto);

        updateUser.setUsername(userDto.getUsername());
        updateUser.setEmail(userDto.getEmail());
        updateUser.setRole(userDto.getRole());
        userRepository.save(updateUser);
    }

    /**
     *
     * @param validateUser
     * @param requestingUser
     * @param userDto
     * @throws ValidationException
     * @throws AuthorizationException
     */
    private void ValidateUser(User validateUser, User requestingUser, UserDto userDto) throws ValidationException, AuthorizationException {
        if (validateUser == null)
            throw new ValidationException("User for deletion not found");
        if (requestingUser == null)
            throw new ValidationException("Requesting user not found");
        ValidateUserDTO(userDto);
        ValidateEmail(validateUser.getEmail(), userDto.getEmail());
        ValidateUserPermission(userDto, validateUser, requestingUser);
        ValidateRole(userDto.getRole(), validateUser.getRole(), requestingUser.getRole());
        ValidateUsername(userDto.getUsername(), requestingUser.getUsername());
    }
    private void ValidateUserPermission(UserDto userDto, User updateUser, User requestingUser) throws AuthorizationException{
        if (!(updateUser.getId() == requestingUser.getId()) && !WebApiHelper.ADMIN_ROLES.contains(requestingUser.getRole()))
            throw new AuthorizationException("User does not have permission to update user");
        if (!WebApiHelper.ADMIN_ROLES.contains(requestingUser.getRole()))
            throw new AuthorizationException("User does not have permission to chance roles");
        if (updateUser.getRole() == Role.CUSTOMER && userDto.getRole() != Role.CUSTOMER)
            throw new AuthorizationException("User does not have permission to have that role");
        if (WebApiHelper.PROTECTED_USER_ROLES.contains(userDto.getRole()))
            throw new AuthorizationException("Super admin is a protected role");
    }
    private void ValidateUserDTO(UserDto userDto)throws ValidationException{
        if (userDto == null)
            throw new ValidationException("User Dto is null");
    }
    private void ValidateEmail(String oldEmail, String newEmail) throws ValidationException{
        if (newEmail == null || newEmail.replaceAll("\\s","").isEmpty())
            throw new ValidationException("New Email is null or empty");
        if (oldEmail == null || oldEmail.replaceAll("\\s","").isEmpty())
            throw new ValidationException("New Email is null or empty");
        try {
            InternetAddress oldEmailAddr = new InternetAddress(oldEmail);
            oldEmailAddr.validate();
        } catch (AddressException ex) {
            throw new ValidationException("Old email address is not an email");
        }
        try {
            InternetAddress newEmailAddr = new InternetAddress(newEmail);
            newEmailAddr.validate();
        } catch (AddressException ex) {
            throw new ValidationException("New email address is not an email");
        }
    }
    private void ValidateRole(Role userDto, Role oldRole, Role newRole) throws ValidationException{
        if (newRole == null)
            throw new ValidationException("New Email is null");
        if (oldRole == null)
            throw new ValidationException("Old role is null");
        if (userDto == null)
            throw new ValidationException("UserDto role is null");
    }
    private void ValidateUsername(String oldUserName, String newUserName) throws ValidationException{
        if (newUserName == null || newUserName.replaceAll("\\s","").isEmpty())
            throw new ValidationException("New Username is null or empty");
        if (oldUserName == null || oldUserName.replaceAll("\\s","").isEmpty())
            throw new ValidationException("New Username is null or empty");
    }


    /**
     *
     * @param userToBeDeletedId
     * @param requestingUserId
     * @throws ValidationException
     * @throws AuthorizationException
     */
    public void  deleteUser(Integer userToBeDeletedId, Integer requestingUserId) throws ValidationException, AuthorizationException {
        User deleteUser = findUserById(userToBeDeletedId);
        User requestingUser = findUserById(requestingUserId);
        validateUserForDeleting(deleteUser, requestingUser);

        System.out.println(String.format("DELETION: Requesting User: <%s>. Requesting User for deletion User: <%s>", requestingUser, deleteUser));
        userRepository.deleteById(userToBeDeletedId);
        System.out.println("DELETION: DONE");
    }
    private void validateUserForDeleting(User deleteUser, User requestingUser) throws AuthorizationException, ValidationException {

        if (deleteUser == null)
            throw new ValidationException("User for deletion not found");
        if (requestingUser == null)
            throw new ValidationException("Requesting user not found");
        if (!WebApiHelper.ADMIN_ROLES.contains(requestingUser.getRole()))
            throw new AuthorizationException("Deleting user is not allowed to delete");
        if (deleteUser == requestingUser)
            throw new AuthorizationException("Can't delete your own account");
        if (WebApiHelper.PROTECTED_USER_ROLES.contains(deleteUser.getRole()))
            throw new AuthorizationException("User not allowed for deletion");
        if (deleteUser.getUserStatus())
            throw new ValidationException("User is still active");
    }


    /**
     *
     * @param deactivatedUserId
     * @param requestingUserId
     * @throws AuthorizationException
     * @throws ValidationException
     */
    public void deactivateUser(Integer deactivatedUserId, Integer requestingUserId) throws AuthorizationException, ValidationException{
        User deactivatedUser = findUserById(deactivatedUserId);
        User requestingUser = findUserById(requestingUserId);
        validateUserForDeactivation(deactivatedUser, requestingUser);
        System.out.println(String.format("DEACTIVATION: Requesting User: <%s>. Requesting User for deactivation User: <%s>", requestingUser, deactivatedUser));
        deactivatedUser.setUserStatus(false);
        userRepository.save(deactivatedUser);
        System.out.println("DEACTIVATION: DONE");
    }
    private void validateUserForDeactivation(User deactivatedUser, User requestingUser) throws AuthorizationException, ValidationException {
        if (deactivatedUser == null)
            throw new ValidationException("User for deletion not found");
        if (requestingUser == null)
            throw new ValidationException("Requesting user not found");
        if (WebApiHelper.PROTECTED_USER_ROLES.contains(deactivatedUser.getRole()))
            throw new AuthorizationException("User not allowed for deactivation");
        if (deactivatedUser.getUserStatus() == false)
            throw new ValidationException("Requesting user already sat to deactivated");
        if (!(deactivatedUser.getId() == requestingUser.getId()) && !WebApiHelper.ADMIN_ROLES.contains(requestingUser.getRole()))
            throw new AuthorizationException("User does not have permission to deactivate user");
    }

    public void activateUser(Integer activatedUserId, Integer requestingUserId) throws ValidationException, AuthorizationException {
        User activatedUser = findUserById(activatedUserId);
        User requestingUser = findUserById(requestingUserId);
        validateUserForActivation(activatedUser, requestingUser);
        System.out.println(String.format("DEACTIVATION: Requesting User: <%s>. Requesting User for Activate User: <%s>", requestingUser, activatedUser));
        activatedUser.setUserStatus(true);
        userRepository.save(activatedUser);
        System.out.println("ACTIVATION: DONE");
    }

    private void validateUserForActivation(User activatedUser, User requestingUser) throws ValidationException, AuthorizationException {
        if (activatedUser == null)
            throw new ValidationException("User for activation not found");
        if (requestingUser == null)
            throw new ValidationException("Requesting user not found");
        if (activatedUser.getUserStatus() == true)
            throw new ValidationException("Requesting user already activated");
        if (!(activatedUser.getId() == requestingUser.getId()) && !WebApiHelper.ADMIN_ROLES.contains(requestingUser.getRole()))
            throw new AuthorizationException("User does not have permission to deactivate user");
    }
}
