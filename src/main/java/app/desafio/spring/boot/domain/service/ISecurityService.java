package app.desafio.spring.boot.domain.service;

import app.desafio.spring.boot.domain.model.User;

public interface ISecurityService {

    User getAuthenticatedUser();

}
