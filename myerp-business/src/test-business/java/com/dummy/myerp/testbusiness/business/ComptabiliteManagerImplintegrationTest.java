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
import org.junit.jupiter.api.Assertions;
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
     * test de la fonction ConnectioToDB
     * entrant: aucun entrant
     * sortant: on récupère une liste d'écriutre comptable
     * attendu: la taille de la liste est différente de null
     * @throws Exception
     */
    @Test
    public void ConnectioToDB() throws FunctionalException {

        //ARRANGE

        //ACT
        List<EcritureComptable> ecritureComptableList = manager.getListEcritureComptable();

        //ASSERT
        Assert.assertNotEquals(ecritureComptableList.size(), null);

    }

    /**
     * test de la fonction InsertEcritureComptableWithNewReference
     * entrant: unee écriture comptable dont la référence n'existe pas
     * sortant: taille de la liste avant insertion et insertion de l'écriture en base
     * attendu: la taille de la liste d'écriture comptable est plus grande de 1 qu'avant insertion
     * @throws Exception
     */
    @Test
    public void InsertEcritureComptableWithNewReference() throws FunctionalException {

        //ARRANGE
        vEcritureComptable.setReference("AC-2020/00088");
        vEcritureComptable.setLibelle("ref_int_insert_test");
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(401), null, new BigDecimal(123), null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(411), null, null, new BigDecimal(123)));

        //ACT
        int tailleDeLalisteAvantInsert = manager.getListEcritureComptable().size();
        manager.insertEcritureComptable(vEcritureComptable);

        //ASSERT
        Assert.assertEquals(manager.getListEcritureComptable().size(), tailleDeLalisteAvantInsert + 1);
        Assert.assertEquals(vEcritureComptable.getReference(), "AC-2020/00088");
    }

    /**
     * test de la fonction InsertEcritureComptableWithExistingReference
     * entrant: une écriture comptable dont la référence existe déjà en base de données
     * sortant: insertion de l'écriture comptable ne respectant pas une règle de la gestion
     * attendu: une functionalException est levé
     * @throws Exception
     */
    @Test(expected = FunctionalException.class)
    public void InsertEcritureComptableWithExistingReference() throws FunctionalException {

        //ARRANGE
        journalComptable = new JournalComptable("VE", "Vente");
        vEcritureComptable.setJournal(journalComptable);
        vEcritureComptable.setDate(new Date());
        vEcritureComptable.setReference("VE-2016/00002");
        vEcritureComptable.setLibelle("CTMA Appli Xxx");

        //ACT
        manager.insertEcritureComptable(vEcritureComptable);

        //ASSERT
        Assertions.assertThrows(FunctionalException.class, () -> {manager.insertEcritureComptable(vEcritureComptable);});
    }

    /**
     * test de la fonction UpdateEcritureComptable
     * entrant: une écriture comptable déjà en base de données et un nouveau libéllé pour celle-ci
     * sortant: insertion de l'écriture comptable avec le nouveau libéllé
     * attendu: l'écriture comptable est mise à jour en BDD
     * @throws Exception
     */
    @Test
    public void UpdateEcritureComptable() throws FunctionalException {

        //ARRANGE
        EcritureComptable ecritureComptable = manager.getListEcritureComptable().get(0);
        String ancienLibelle = ecritureComptable.getLibelle();
        ecritureComptable.setLibelle("nouveau libelle");

        //ACT
        manager.updateEcritureComptable(ecritureComptable);
        String nouveauLibelle = manager.getListEcritureComptable().get(0).getLibelle();

        //ASSERT
        Assert.assertNotEquals(ancienLibelle, nouveauLibelle);

        //ARRANGE
        ecritureComptable.setLibelle(ancienLibelle);

        //ACT
        manager.updateEcritureComptable(ecritureComptable);
    }

    /**
     * test de la fonction DeleteEcritureComptable
     * entrant: une écriture comptable déjà en base de données
     * sortant: suppression de l'écriture comptable en BDD
     * attendu: l'écriture comptable n'est plus en BDD
     * @throws Exception
     */
    @Test
    public void DeleteEcritureComptable() throws FunctionalException {

        //ARRANGE
        vEcritureComptable.setReference("AC-2020/52371");
        vEcritureComptable.setLibelle("Test_Delete");
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(401), null, new BigDecimal(123), null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(411), null, null, new BigDecimal(123)));

        //ACT
        manager.insertEcritureComptable(vEcritureComptable);
        EcritureComptable ecritureComptable = manager.getListEcritureComptable().get(manager.getListEcritureComptable().size() - 1);
        int sizeOfList = manager.getListEcritureComptable().size();
        manager.deleteEcritureComptable(vEcritureComptable.getId());

        //ASSERT
        Assert.assertEquals(sizeOfList, manager.getListEcritureComptable().size() + 1);
    }


    /**
     * test de la fonction AddReference afin de tester l'ajout d'une nouvelle séquence écriture comptable
     * entrant: une nouvelle écriture comptable pour laquelle une séquence écriture comptable n'existe pas
     * sortant: ajout de l'écriture comptable
     * attendu: création d'une nouvelle séquence écriture comptable en BDD
     * @throws Exception
     */
    @Test
    public void AddReference() throws FunctionalException {

        //ARRANGE
        vEcritureComptable.setLibelle("libelle addReference");
        journalComptable = (new JournalComptable("BQ", "Banque"));
        vEcritureComptable.setJournal(journalComptable);
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(606), null, new BigDecimal(123), null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(706), null, null, new BigDecimal(123)));

        //ACT
        manager.addReference(vEcritureComptable);

        //ASSERT
        Assert.assertEquals(vEcritureComptable.getReference(), "BQ-2020/00001");
    }

}