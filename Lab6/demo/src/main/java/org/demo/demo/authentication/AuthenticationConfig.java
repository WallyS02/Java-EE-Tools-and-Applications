package org.demo.demo.authentication;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.security.enterprise.authentication.mechanism.http.BasicAuthenticationMechanismDefinition;
import jakarta.security.enterprise.identitystore.DatabaseIdentityStoreDefinition;
import jakarta.security.enterprise.identitystore.Pbkdf2PasswordHash;

@ApplicationScoped
@BasicAuthenticationMechanismDefinition(realmName = "Music")
@DatabaseIdentityStoreDefinition(
        dataSourceLookup = "jdbc/Music",
        callerQuery = "select password from musicians where login = ?",
        groupsQuery = "select role from musicians__roles where id = (select id from musicians where login = ?)",
        hashAlgorithm = Pbkdf2PasswordHash.class
)
public class AuthenticationConfig {

}
