package org.mycorp.tests.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mycorp.models.User;
import org.mycorp.repository.RepositoryUser;
import org.mycorp.services.AuthService;
import org.mycorp.services.UserService;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Mock
    private RepositoryUser repositoryUserMock;
    @Mock
    private AuthService authServiceMock;

    @InjectMocks
    private UserService userService;

    @Test
    public void createTestTrueBehaviour() throws Exception {
        User testUser = new User("John", "67890");

        when(authServiceMock.passwordEncoding(anyString())).thenReturn("encodedPassword");
        when(repositoryUserMock.findByNickname("John")).thenReturn(null);

        userService.create(testUser);

        verify(repositoryUserMock, times(1)).findByNickname("John");
        verify(authServiceMock, times(1)).passwordEncoding("67890");
    }

    @Test
    public void createTestExceptionBehaviour() {
        User testUser = new User("Egor", "12345");
        User badTestUser = new User(null, null);

        when(repositoryUserMock.findByNickname("Egor")).thenReturn(new User("Egor", "existingPassword"));
        //when(authServiceMock.passwordEncoding(anyString())).thenReturn("encodedPassword");

        assertThrows(Exception.class, () -> userService.create(testUser));
        assertThrows(Exception.class, () -> userService.create(badTestUser));

        verify(repositoryUserMock, times(1)).findByNickname("Egor");
        verify(authServiceMock, times(0)).passwordEncoding(anyString());
    }

    @Test
    public void readTest(){
        int true_user_id = 1;
        int false_user_id = 2;
        User readedUser = new User(1, null,null);

        when(repositoryUserMock.findById(true_user_id)).thenReturn(Optional.of(new User(1, "Egor", "gege")));
        Object findUser = userService.read(true_user_id);
        assertEquals(findUser, readedUser);

        when(repositoryUserMock.findById(false_user_id)).thenReturn(Optional.empty());
        Object notReadUser = userService.read(false_user_id);
        assertNull(notReadUser);

        verify(repositoryUserMock, times(1)).findById(1);
        verify(repositoryUserMock, times(1)).findById(2);
    }

    @Test
    public void updateTestTrueBehaviour() throws Exception {
        int id_user=1;
        User newUser = new User("Egor2", "password");

        when(authServiceMock.passwordEncoding("password")).thenReturn("encodedPassword");
        when(repositoryUserMock.findById(id_user)).thenReturn(Optional.of(new User(1, "Egor", "eeeeeeee")));

        boolean updated = userService.update(newUser, id_user);
        assertTrue(updated);

        when(repositoryUserMock.findById(id_user)).thenReturn(Optional.empty());

        boolean notUpdated = userService.update(newUser, id_user);
        assertFalse(notUpdated);

        verify(repositoryUserMock, times(2)).findById(1);
        verify(authServiceMock, times(2)).passwordEncoding(anyString());
    }

    @Test
    public void updateTestExceptionBehaviour() throws Exception {
        int id_user=1;
        User newUserWithBadInput = new User(null,null);
        User newUserWithDupNickname = new User("Egor","11");

        //when(authServiceMock.passwordEncoding(anyString())).thenReturn("encodedPassword");
        //when(repositoryUserMock.findById(anyInt())).thenReturn(Optional.of(new User(1, "Egor", "eeeeeeee")));

        assertThrows(Exception.class, () -> userService.create(newUserWithBadInput));

        when(repositoryUserMock.findByNickname(newUserWithDupNickname.getNickname())).thenReturn(new User(2,"Egor","322"));
        assertThrows(Exception.class, () -> userService.create(newUserWithDupNickname));

        verify(repositoryUserMock, times(0)).findById(anyInt());
        verify(authServiceMock, times(0)).passwordEncoding(anyString());
        verify(repositoryUserMock, times(1)).findByNickname(anyString());

    }

    @Test
    public void authorisationTest(){
        User userToAuth = new User("Egor", "12345");
        User findUser = new User(1,"Egor", "322");
        User testUser = new User(1, null, null);

        when(repositoryUserMock.findByNickname(userToAuth.getNickname())).thenReturn(findUser);
        when(authServiceMock.authentication(userToAuth, findUser)).thenReturn(findUser);

        User authorisedUser = userService.authorisation(userToAuth);

        assertEquals(testUser, authorisedUser);

        verify(repositoryUserMock, times(1)).findByNickname(userToAuth.getNickname());
        verify(authServiceMock, times(1)).authentication(userToAuth, findUser);

        User wrongInputUser = userService.authorisation(testUser);

        assertNull(wrongInputUser);

        verify(repositoryUserMock, times(1)).findByNickname(anyString());
        verify(authServiceMock, times(1)).authentication(any(), any());

        when(repositoryUserMock.findByNickname(userToAuth.getNickname())).thenReturn(null);

        User notFoundUser = userService.authorisation(userToAuth);

        assertNull(notFoundUser);

        verify(repositoryUserMock, times(2)).findByNickname(anyString());
        verify(authServiceMock, times(1)).authentication(any(), any());

        when(repositoryUserMock.findByNickname(userToAuth.getNickname())).thenReturn(findUser);
        when(authServiceMock.authentication(userToAuth, findUser)).thenReturn(null);

        User notVerificateUser = userService.authorisation(userToAuth);

        assertNull(notVerificateUser);

        verify(repositoryUserMock, times(3)).findByNickname(anyString());
        verify(authServiceMock, times(2)).authentication(any(), any());
    }


    @Test
    public void updateEntityTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        User testUser = new User("Egor", "12345");
        User testUserUpdate = new User("Egorererr", "12354545");

        Method method = userService.getClass().getDeclaredMethod("updateEntity", User.class, User.class);
        method.setAccessible(true);
        Object updatedUser = method.invoke(userService, testUser, testUserUpdate);

        assertEquals(testUser, updatedUser);
        assertEquals(testUserUpdate, updatedUser);
    }


    @Test
    public void userToJSONTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        User testUser = new User("Egor", "12345");
        User testJsonUser = new User(null, null);

        Method method = userService.getClass().getDeclaredMethod("userToJSON", User.class);
        method.setAccessible(true);
        Object jsonUser = method.invoke(userService, testUser);

        assertEquals(testJsonUser, testUser);
    }

    @Test
    public void findUserByNicknameTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        User testUser = new User("Egor", "12345");
        User userNotInDB = new User("Egorer", "12345");

        when(repositoryUserMock.findByNickname("Egor")).thenReturn(new User("Egor", "*****"));
        when(repositoryUserMock.findByNickname("Egorer")).thenReturn(null);

        Method method = userService.getClass().getDeclaredMethod("findUserByNickname", String.class);
        method.setAccessible(true);
        Object findUser = method.invoke(userService, testUser.getNickname());
        Object notFindUser = method.invoke(userService, userNotInDB.getNickname());


        assertEquals(testUser.getNickname(), ((User) findUser).getNickname());
        assertNull(notFindUser);

        verify(repositoryUserMock, times(1)).findByNickname("Egor");
        verify(repositoryUserMock, times(1)).findByNickname("Egorer");
    }




}
