package ru.netology.test;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ru.netology.page.DashboardPage;
import ru.netology.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.data.DataHelper.*;

public class TransferTest {
    DashboardPage dashboardPage;
    CardInfo firstCardInfo;
    CardInfo secondCardInfo;
    int firstCardBalance;
    int secondCardBalance;


    @BeforeEach
    void setup() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var verificationCode = getVerificationCode();
        var authInfo = getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        dashboardPage = verificationPage.validVerify(verificationCode);
        firstCardInfo = getFirstCardInfo();
        secondCardInfo = getSecondcardInfo();
        firstCardBalance = dashboardPage.getCardBalance(firstCardInfo);
        secondCardBalance = dashboardPage.getCardBalance(secondCardInfo);

    }

    @Test
    void shouldTransferFromFirstToSecond() {
        int amount = TransferAmount(firstCardBalance);
        int expectedBalanceFirstCard = firstCardBalance - amount;
        int expectedBalanceSecondCard = secondCardBalance + amount;
        var transferPage = dashboardPage.selectCard(secondCardInfo);
        dashboardPage = transferPage.makeTransfer(String.valueOf(amount), firstCardInfo);
        var actualBalanceFirstCard = dashboardPage.getCardBalance(firstCardInfo);
        var actualBalanceSecondCard = dashboardPage.getCardBalance(secondCardInfo);
        assertEquals(expectedBalanceFirstCard, actualBalanceFirstCard);
        assertEquals(expectedBalanceSecondCard, actualBalanceSecondCard);


    }

    @Test
    void shouldShowErrorMessageIfOverdraft() {
        int amount = TransferOverdraft(firstCardBalance);

        var transferPage = dashboardPage.selectCard(secondCardInfo);
        transferPage.makeTransfer(String.valueOf(amount), firstCardInfo);
        transferPage.showErrorMessage();
        var actualBalanceFirstCard = dashboardPage.getCardBalance(firstCardInfo);
        var actualBalanceSecondCard = dashboardPage.getCardBalance(secondCardInfo);

        assertEquals(firstCardBalance, actualBalanceFirstCard);
        assertEquals(secondCardBalance, actualBalanceSecondCard);

    }


}
