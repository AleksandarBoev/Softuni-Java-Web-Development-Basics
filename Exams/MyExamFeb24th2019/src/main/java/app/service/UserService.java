package app.service;

import app.domain.models.service.UserServiceModel;

public interface UserService extends GenericService<UserServiceModel, String> {

    UserServiceModel loginUser(UserServiceModel userServiceModel);
}
