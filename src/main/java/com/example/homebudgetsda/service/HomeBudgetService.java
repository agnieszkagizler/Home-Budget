package com.example.homebudgetsda.service;

import com.example.homebudgetsda.dto.*;
import com.example.homebudgetsda.repository.HomeBudgetItemJpaRepository;
import com.example.homebudgetsda.repository.HomeBudgetJpaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class HomeBudgetService {

    private final HomeBudgetJpaRepository homeBudgetJpaRepository;
    private final HomeBudgetItemJpaRepository homeBudgetItemJpaRepository;

    public HomeBudgetService(HomeBudgetJpaRepository homeBudgetJpaRepository, HomeBudgetItemJpaRepository homeBudgetItemJpaRepository) {
        this.homeBudgetJpaRepository = homeBudgetJpaRepository;
        this.homeBudgetItemJpaRepository = homeBudgetItemJpaRepository;
    }


    public BudgetEntity createNew(BudgetEntity budgetEntity) {
        budgetEntity.setId(null);
        return homeBudgetJpaRepository.save(budgetEntity);
    }

    public void createNewBudgetItem(BudgetItemEntity budgetItem, Long id) {
        BudgetEntity currentBudget = getBudgetEntityById(id);
        BudgetItemEntity savedBudgetItem = createNewBudgetItem(budgetItem);
        updateCurrentBudgetWithBudgetItem(currentBudget, savedBudgetItem);
    }

    private BudgetEntity getBudgetEntityById(Long id) {
        return homeBudgetJpaRepository.getById(id);
    }

    private BudgetItemEntity createNewBudgetItem(BudgetItemEntity budgetItem) {
        budgetItem.setId(null);
        return homeBudgetItemJpaRepository.save(budgetItem);
    }

    private void updateCurrentBudgetWithBudgetItem(BudgetEntity currentBudget, BudgetItemEntity savedBudgetItemEntity) {
        List<BudgetItemEntity> budgetItems = getCurrentBudgetBudgetItems(currentBudget);
        budgetItems.add(savedBudgetItemEntity);
        currentBudget.setBudgetEntities(budgetItems);
        homeBudgetJpaRepository.save(currentBudget);
    }

    private List<BudgetItemEntity> getCurrentBudgetBudgetItems(BudgetEntity currentBudget) {
        return Optional.ofNullable(currentBudget.getBudgetEntities())
                .orElse(new ArrayList<>());
    }

    public List<BudgetItemEntity> getAll() {
        return homeBudgetItemJpaRepository.findAll();
    }

    public Optional<BudgetItemEntity> getById(Long id) {
        return homeBudgetItemJpaRepository.findById(id);
    }

    public Optional<BudgetItemEntity> getByCategory(BudgetCategory category) {
        return homeBudgetItemJpaRepository.findByCategory(category);
    }

    public void modifyBudgetItem(BudgetItemEntity budgetItem, Long id) {
        BudgetItemEntity existing = homeBudgetItemJpaRepository.getById(id);
        existing.setAmount(budgetItem.getAmount());
        existing.setTime(budgetItem.getTime());
        existing.setType(budgetItem.getType());
        homeBudgetItemJpaRepository.save(existing);
    }

    public void deleteBudgetItem(Long id) {
        homeBudgetJpaRepository.deleteById(id);
    }

    public BudgetSummary summaryByCategoryAndType(Long id, BudgetCategory category, LocalDate from, LocalDate to, BudgetEntityType type) {
        BudgetSummary summary = new BudgetSummary();
        switch (type) {
            case INCOME:
                summary.setIncomes(summaryByCategory(id, category, from, to).getIncomes());
                break;
            case OUTCOME:
                summary.setOutcomes(summaryByCategory(id, category, from, to).getOutcomes());
                break;
        }
        return summary;
    }

    public BudgetSummary summaryByCategory(Long id, BudgetCategory category, LocalDate from, LocalDate to) {
        return summary(id, from, to, category);
    }

    public BudgetSummary summary(Long budgetId, LocalDate from, LocalDate to) {
        return summary(budgetId, from, to, null);
    }

    private BudgetSummary summary(Long budgetId, LocalDate from, LocalDate to, BudgetCategory category) {
        BudgetSummary budgetSummary = new BudgetSummary();
        BudgetEntity budgetEntity = homeBudgetJpaRepository.findById(budgetId).orElseThrow();
        List<BudgetItemEntity> budgetItems = budgetEntity.getBudgetEntities();

        List<BudgetItemEntity> incomes = filterBudgetItems(BudgetEntityType.INCOME, budgetItems, from, to, category);
        List<BudgetItemEntity> outcomes = filterBudgetItems(BudgetEntityType.OUTCOME, budgetItems, from, to, category);

        double balance = getBalance(incomes, outcomes);

        budgetSummary.setBalance(balance);
        budgetSummary.setIncomes(incomes);
        budgetSummary.setOutcomes(outcomes);
        return budgetSummary;
    }

    private double getBalance(List<BudgetItemEntity> incomes, List<BudgetItemEntity> outcomes) {
        double filteredIncome = incomes.stream().mapToDouble(x -> x.getAmount()).sum();
        double filteredOutcome = outcomes.stream().mapToDouble(x -> x.getAmount()).sum();
        double balance = filteredIncome - filteredOutcome;
        return balance;
    }

    private List<BudgetItemEntity> filterBudgetItems(BudgetEntityType type, List<BudgetItemEntity> budgetEntities, LocalDate from, LocalDate to, BudgetCategory category) {

        Stream<BudgetItemEntity> budgetItemEntityStream = budgetEntities.stream()
                .filter(x -> x.getType().equals(type));
        if (Objects.nonNull(category)) {
            budgetItemEntityStream = budgetItemEntityStream.filter(x -> category.equals(x.getCategory()));
        }
        if (Objects.nonNull(from)) {
            budgetItemEntityStream = budgetItemEntityStream.filter(x -> x.getTime().toLocalDate().isAfter(from) || x.getTime().toLocalDate().isEqual(from));
        }
        if (Objects.nonNull(to)) {
            budgetItemEntityStream = budgetItemEntityStream.filter(x -> x.getTime().toLocalDate().isBefore(to) || x.getTime().toLocalDate().isEqual(to));
        }
        return budgetItemEntityStream.collect(Collectors.toList());
    }
}
