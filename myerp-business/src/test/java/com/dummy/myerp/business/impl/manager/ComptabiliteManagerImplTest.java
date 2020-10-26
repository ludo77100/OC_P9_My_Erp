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
     * Before each test initialize object vEcritureComptable //TODO Traduire en francais
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
     * After each test reset object vEcritureComptable //TODO traduire en francais
     */
    @After
    public void ResetvEcritureComptable(){
        vEcritureComptable=new EcritureComptable();
    }

    //TODO ecrire la javadoc
    @Test
    public void checkEcritureComptableUnit() throws Exception {
        // ARRANGE

        //ACT
        manager.checkEcritureComptableUnit(vEcritureComptable);

        //ASSERT
        Assertions.assertDoesNotThrow(() -> {manager.checkEcritureComptableUnit(vEcritureComptable);});
    }

    //TODO ecrire la javadoc
    @Test(expected = FunctionalException.class)
    public void checkEcritureComptableUnitViolation() throws Exception {

        //ARRANGE
        EcritureComptable vEcritureComptable;
        vEcritureComptable = new EcritureComptable();

        //ACT
        manager.checkEcritureComptableUnit(vEcritureComptable);

        //ASSERT
        //TODO créer l'assertion
    }

    //TODO ecrire la javadoc
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
        //TODO créer l'assertion
    }

    //TODO ecrire la javadoc
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
        //TODO créer l'assertion
    }

    //TODO ecrire la javadoc
    @Test
    public void checkEcritureComptableUnitRG5isOk() throws Exception {

        //ARRANGE

        //ACT
        manager.checkEcritureComptableUnitRG5(vEcritureComptable);

        //ASSERT
        Assertions.assertDoesNotThrow(() -> {manager.checkEcritureComptableUnitRG5(vEcritureComptable);});

    }

    //TODO ecrire la javadoc
    //TODO modifier nom méthode pour différencier d'un test normalement ok d'un mauvais
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
