package com.dummy.myerp.business.impl.manager;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import com.dummy.myerp.model.bean.comptabilite.CompteComptable;
import com.dummy.myerp.model.bean.comptabilite.EcritureComptable;
import com.dummy.myerp.model.bean.comptabilite.JournalComptable;
import com.dummy.myerp.model.bean.comptabilite.LigneEcritureComptable;
import com.dummy.myerp.technical.exception.FunctionalException;
import org.junit.jupiter.api.Assertions;


public class ComptabiliteManagerImplTest {

    private ComptabiliteManagerImpl manager = new ComptabiliteManagerImpl();

    private EcritureComptable vEcritureComptable ;

    /**
     * Initialisation des variables avant chaque tests
     */
    @Before
    public void initCompatibiliteManagerImpl(){
        vEcritureComptable = new EcritureComptable();
        vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        vEcritureComptable.setDate(new Date());
        vEcritureComptable.setReference("AC-2020/00001");
        vEcritureComptable.setLibelle("Libelle");
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal(123),
                null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
                null, null,
                new BigDecimal(123)));
    }

    /**
     * Réinitialisation des variables après chaque test
     */
    @After
    public void ResetvEcritureComptable(){
        vEcritureComptable=new EcritureComptable();
    }

    /**
     * test de la fonction checkEcritureComptableUnit
     * entrant:une ecriture comptable (vEcritureComptable)
     * sortant: on vérifie que l'écriture comptable respecte les règles de gestion
     * attendu: l'écriture comptable respecte les règles de gestion
     * @throws Exception
     */
    @Test
    public void checkEcritureComptableUnit() throws Exception {
        // ARRANGE

        //ACT
        manager.checkEcritureComptableUnit(vEcritureComptable);

        //ASSERT
        Assertions.assertDoesNotThrow(() -> {manager.checkEcritureComptableUnit(vEcritureComptable);});
    }

    /**
     * test de la fonction checkEcritureComptableUnitViolation
     * entrant:une ecriture comptable (vEcritureComptable)
     * sortant: on vérifie que l'écriture comptable respecte les règles de gestion
     * attendu: l'écriture comptable ne respecte pas les règles de gestion et lève une FunctionalException
     * @throws Exception
     */
    @Test(expected = FunctionalException.class)
    public void checkEcritureComptableUnitViolation() throws Exception {

        //ARRANGE
        EcritureComptable vEcritureComptable;
        vEcritureComptable = new EcritureComptable();

        //ACT
        manager.checkEcritureComptableUnit(vEcritureComptable);

        //ASSERT
        Assertions.assertThrows(FunctionalException.class, () -> {manager.checkEcritureComptableUnit(vEcritureComptable);});
    }

    /**
     * test de la fonction checkEcritureComptableUnitRG2
     * entrant:une ecriture comptable (vEcritureComptable)
     * sortant: on vérifie que l'écriture comptable respecte la règle de gestion numéro 2
     * attendu: l'écriture comptable ne respecte pas la règle de gestion numéro 2 et lève une FunctionalException
     * @throws Exception
     */
    @Test(expected = FunctionalException.class)
    public void checkEcritureComptableUnitRG2() throws Exception {

        //ARRANGE
        EcritureComptable vEcritureComptable;
        vEcritureComptable = new EcritureComptable();
        vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        vEcritureComptable.setDate(new Date());
        vEcritureComptable.setLibelle("Libelle");
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                                                                                 null, new BigDecimal(123),
                                                                                 null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
                                                                                 null, null,
                                                                                 new BigDecimal(1234)));

        //ACT
        manager.checkEcritureComptableUnit(vEcritureComptable);

        //ASSERT
        Assertions.assertThrows(FunctionalException.class, () -> {manager.checkEcritureComptableUnit(vEcritureComptable);});
    }

    /**
     * test de la fonction checkEcritureComptableUnitRG3
     * entrant:une ecriture comptable (vEcritureComptable)
     * sortant: on vérifie que l'écriture comptable respecte la règle de gestion numéro 3
     * attendu: l'écriture comptable ne respecte pas la règle de gestion numéro 3 et lève une FunctionalException
     * @throws Exception
     */
    @Test(expected = FunctionalException.class)
    public void checkEcritureComptableUnitRG3() throws Exception {

        //ARRANGE
        EcritureComptable vEcritureComptable;
        vEcritureComptable = new EcritureComptable();
        vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        vEcritureComptable.setDate(new Date());
        vEcritureComptable.setLibelle("Libelle");
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal(123),
                null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal(123),
                null));

        //ACT
        manager.checkEcritureComptableUnit(vEcritureComptable);

        //ASSERT
        Assertions.assertThrows(FunctionalException.class, () -> {manager.checkEcritureComptableUnit(vEcritureComptable);});
    }

    /**
     * test de la fonction checkEcritureComptableUnitRG5isOk
     * entrant:une ecriture comptable (vEcritureComptable)
     * sortant: on vérifie que l'écriture comptable respecte la règle de gestion numéro 5
     * attendu: l'écriture comptable respecte la règle de gestion numéro 5 et ne lève pas de FunctionalException
     * @throws Exception
     */
    @Test
    public void checkEcritureComptableUnitRG5isOk() throws Exception {

        //ARRANGE

        //ACT
        manager.checkEcritureComptableUnitRG5(vEcritureComptable);

        //ASSERT
        Assertions.assertDoesNotThrow(() -> {manager.checkEcritureComptableUnitRG5(vEcritureComptable);});

    }

    /**
     * test de la fonction checkEcritureComptableUnitRG5IsWrong
     * entrant:une ecriture comptable (vEcritureComptable)
     * sortant: on vérifie que l'écriture comptable respecte la règle de gestion numéro 5
     * attendu: l'écriture comptable ne respecte pas la règle de gestion numéro 5 et lève une FunctionalException
     * @throws Exception
     */
    @Test(expected = FunctionalException.class)
    public void checkEcritureComptableUnitRG5IsWrong() throws Exception {

        //ARRANGE
        EcritureComptable vEcritureComptable ;
        vEcritureComptable = new EcritureComptable();
        vEcritureComptable.setReference("PP-2020/00001");
        vEcritureComptable.setId(1);
        vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        vEcritureComptable.setDate(new Date());

        //ACT
        manager.checkEcritureComptableUnitRG5(vEcritureComptable);

        //ASSERT
        Assertions.assertThrows(FunctionalException.class, () -> {manager.checkEcritureComptableUnitRG5(vEcritureComptable);});
    }
}
