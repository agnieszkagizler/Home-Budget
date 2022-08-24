package com.example.homebudgetsda.controller;

import com.example.homebudgetsda.dto.*;
import com.example.homebudgetsda.service.HomeBudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
public class HomeBudgetController {

    private final HomeBudgetService homeBudgetService;

    @Autowired
    public HomeBudgetController(HomeBudgetService homeBudgetService) {
        this.homeBudgetService = homeBudgetService;
    }

    @GetMapping(path = "/budget/{id}/summary")
    public BudgetSummary summary(@RequestParam(required = false, name = "from")
                                 @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
                                 @RequestParam(required = false, name = "to")
                                 @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,
                                 @PathVariable Long id) {
        return homeBudgetService.summary(id, from, to);
    }

    @GetMapping(path = "/budget/{id}/summary/categories/{category}")
    public BudgetSummary summaryByCategory(@RequestParam(required = false, name = "from")
                                           @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
                                           @RequestParam(required = false, name = "to")
                                           @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,
                                           @PathVariable Long id,
                                           @PathVariable BudgetCategory category) {
        return homeBudgetService.summaryByCategory(id, category, from, to);
    }

    @GetMapping(path = "/budget/{id}/summary/categories/{category}/{type}")
    public BudgetSummary summaryTypeByCategory(@RequestParam(required = false, name = "from")
                                               @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
                                               @RequestParam(required = false, name = "to")
                                               @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,
                                               @PathVariable Long id,
                                               @PathVariable BudgetCategory category,
                                               @PathVariable BudgetEntityType type) {
        return homeBudgetService.summaryByCategoryAndType(id, category, from, to, type);
    }

    @PostMapping(path = "/budget")
    public void saveBudget(@RequestBody BudgetEntity budgetEntity) {
        homeBudgetService.createNew(budgetEntity);
    }

    @PostMapping(path = "budget/{id}/budgetItem")
    public void saveBudgetItem(@Valid @RequestBody BudgetItemEntity budgetItemEntity, @PathVariable(required = true) Long id) {
        homeBudgetService.createNewBudgetItem(budgetItemEntity, id);
    }

    @GetMapping(path = "/budget/budgetItems")
    public List<BudgetItemEntity> getAll() {
        return homeBudgetService.getAll();
    }

    @GetMapping(path = "/budget/{budgetId}/budgetItem/{id:[0-9]+}")
    public Optional<BudgetItemEntity> getById(@PathVariable Long id) {
        return homeBudgetService.getById(id);
    }

    @PutMapping(path = "/budget/{budgetId}/budgetItem/{id}")
    public void modifyBudgetItem(@PathVariable Long budgetId, @PathVariable Long id, @RequestBody BudgetItemEntity budgetItem) {
        homeBudgetService.modifyBudgetItem(budgetItem, id);
    }

    @DeleteMapping(path = "/budget/{budgetId}/budgetItem/{id}")
    public void deleteItemById(@PathVariable Long id) {
        homeBudgetService.deleteBudgetItem(id);
    }
}
