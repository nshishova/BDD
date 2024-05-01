package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$;


public class TransferPage {
    private final SelenideElement inputAmount = $("[data-test-id='amount'] input");
    private final SelenideElement transferFrom = $("[data-test-id='from'] input");
    private final SelenideElement transferButton = $("[data-test-id='action-transfer']");
    private final SelenideElement errorMassage = $("[data-test-id='error-notification'] " +
            ".notification__content");


    public DashboardPage makeTransfer(String transferAmount, DataHelper.CardInfo cardInfo) {
        inputAmount.setValue(transferAmount);
        transferFrom.setValue(cardInfo.getCardNumber());
        transferButton.click();
        return new DashboardPage();
    }

    public void showErrorMessage() {
        errorMassage.shouldBe(Condition.visible, Duration.ofSeconds(15));
    }


}
