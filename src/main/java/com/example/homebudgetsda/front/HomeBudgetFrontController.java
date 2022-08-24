package com.example.homebudgetsda.front;

import com.example.homebudgetsda.controller.HomeBudgetController;
import com.example.homebudgetsda.dto.BudgetEntity;
import com.example.homebudgetsda.dto.BudgetItemEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.security.Principal;

@Controller
public class HomeBudgetFrontController {

    @Autowired
    private HomeBudgetController homeBudgetController;


    @GetMapping(path = "/front/budget/add-new")
    public String addNewBudgetItemPage(Model model) {
        model.addAttribute("formObject", new NewBudgetItemForm());
        return "newbudgetitem";

    }

    @PostMapping(path = "/front/budgetItem/add-new")
    public String addNewBudgetItem(@Valid @ModelAttribute("newBudgetItemForm") NewBudgetItemForm newBudgetItemForm,
                                   BindingResult errors,
                                   Principal principal,
                                   Model model){
        if (errors.hasErrors()){
            model.addAttribute("newBudgetItemForm", newBudgetItemForm);
            return "newbudgetitem";
        }

        BudgetItemEntity budgetItem = new BudgetItemEntity();
        budgetItem.setAmount(newBudgetItemForm.getAmount());
        homeBudgetController.saveBudgetItem(budgetItem, budgetItem.getId());
        return "redirect:/";
    }
}
