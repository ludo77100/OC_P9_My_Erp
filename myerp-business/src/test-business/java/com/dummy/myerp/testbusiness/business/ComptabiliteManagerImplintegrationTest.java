package com.dummy.myerp.testbusiness.business;

import com.dummy.myerp.business.impl.manager.ComptabiliteManagerImpl;
import com.dummy.myerp.model.bean.comptabilite.CompteComptable;
import com.dummy.myerp.model.bean.comptabilite.EcritureComptable;
import com.dummy.myerp.model.bean.comptabilite.JournalComptable;
import com.dummy.myerp.model.bean.comptabilite.LigneEcritureComptable;
import com.dummy.myerp.technical.exception.FunctionalException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.junit.runner.RunWith;

import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:com/dummy/myerp/business/bootstrapContext.xml"})
@ExtendWith(MockitoExtension.class)
@Transactional
@Rollback
public class ComptabiliteManagerImplintegrationTest extends BusinessTestCase {

    @Mock
    private ComptabiliteManagerImpl manager;
    private EcritureComptable vEcritureComptable;
    private JournalComptable journalComptable;


    /**
     * Initialisation de variable avant chaque test
     */
    @Before
    public void ComptabiliteManagerImplintegrationTest_Init() {
        manager = new ComptabiliteManagerImpl();
        vEcritureComptable = new EcritureComptable();
        journalComptable = new JournalComptable("AC", "Achat");
        vEcritureComptable.setJournal(journalComptable);
        vEcritureComptable.setDate(new Date());
    }

    /**
     * Réinitialisation de variable après chaque test
     */
    @After
    public void ResetvEcritureComptable() {
        journalComptable = new JournalComptable();
        vEcritureComptable = new EcritureComptable();
    }

    /**
     * Test de la connection à la base de données
     *
     * @throws FunctionalException
     */
    @Test
    public void ConnectioToDB() throws FunctionalException {

        List<EcritureComptable> ecritureComptableList = manager.getListEcritureComptable();
        Assert.assertNotEquals(ecritureComptableList.size(), null);

    }

    /**
     * Ce test permet de tester l'insertion d'une écriture comptable avec une référence n'existant pas
     *
     * @throws FunctionalException
     */
    @Test
    public void InsertEcritureComptableWithNewReference() throws FunctionalException {
        vEcritureComptable.setReference("AC-2020/00088");
        vEcritureComptable.setLibelle("ref_int_insert_test");
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(401), null, new BigDecimal(123), null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(411), null, null, new BigDecimal(123)));
        int tailleDeLalisteAvantInsert = manager.getListEcritureComptable().size();
        manager.insertEcritureComptable(vEcritureComptable);
        Assert.assertEquals(manager.getListEcritureComptable().size(), tailleDeLalisteAvantInsert + 1);
        Assert.assertEquals(vEcritureComptable.getReference(), "AC-2020/00088");
        manager.deleteEcritureComptable(vEcritureComptable.getId());

    }

    /**
     * Ce test permet de tester l'insertion d'une écriture comptable avec une référence qui existe déjà
     *
     * @throws FunctionalException
     */
    @Test(expected = FunctionalException.class)
    public void InsertEcritureComptableWithExistingReference() throws FunctionalException {

        journalComptable = new JournalComptable("VE", "Vente");
        vEcritureComptable.setJournal(journalComptable);
        vEcritureComptable.setDate(new Date());
        vEcritureComptable.setReference("VE-2016/00002");
        vEcritureComptable.setLibelle("CTMA Appli Xxx");
        manager.insertEcritureComptable(vEcritureComptable);

    }

    /**
     * Ce test permet de tester la mise à jour d'une ecriture compable
     *
     * @throws FunctionalException
     */
    @Test
    public void UpdateEcritureComptable() throws FunctionalException {

        EcritureComptable ecritureComptable = manager.getListEcritureComptable().get(0);
        String ancienLibelle = ecritureComptable.getLibelle();
        ecritureComptable.setLibelle("nouveau libelle");
        manager.updateEcritureComptable(ecritureComptable);
        String nouveauLibelle = manager.getListEcritureComptable().get(0).getLibelle();
        Assert.assertNotEquals(ancienLibelle, nouveauLibelle);
        ecritureComptable.setLibelle(ancienLibelle);
        manager.updateEcritureComptable(ecritureComptable);
    }

    @Test
    public void DeleteEcritureComptable() throws FunctionalException {
        //TODO

        vEcritureComptable.setReference("AC-2020/52371");
        vEcritureComptable.setLibelle("Test_Delete");
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(401), null, new BigDecimal(123), null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(411), null, null, new BigDecimal(123)));
        manager.insertEcritureComptable(vEcritureComptable);
        EcritureComptable ecritureComptable = manager.getListEcritureComptable().get(manager.getListEcritureComptable().size() - 1);
        int sizeOfList = manager.getListEcritureComptable().size();
        manager.deleteEcritureComptable(vEcritureComptable.getId());
        Assert.assertEquals(sizeOfList, manager.getListEcritureComptable().size() + 1);
    }


    //TODO ne delete pas la sequence donc ne marche q'une fois ...
    @Test
    public void AddReference() throws FunctionalException {

        vEcritureComptable.setLibelle("libelle addReference");
        journalComptable = (new JournalComptable("BQ", "Banque"));
        vEcritureComptable.setJournal(journalComptable);
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(606), null, new BigDecimal(123), null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(706), null, null, new BigDecimal(123)));
        manager.addReference(vEcritureComptable);
        Assert.assertEquals(vEcritureComptable.getReference(), "BQ-2020/00001");
        manager.deleteSequenceEcritureComptable(manager.getSequenceEcritureComptable("BQ", 2020));
    }

}