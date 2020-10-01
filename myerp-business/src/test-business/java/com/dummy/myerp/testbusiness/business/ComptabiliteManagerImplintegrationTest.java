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
import org.junit.runner.RunWith;

import org.mockito.Mock;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:com/dummy/myerp/business/bootstrapContext.xml"})
public class ComptabiliteManagerImplintegrationTest extends BusinessTestCase{

    @Mock
    private ComptabiliteManagerImpl manager;
    private EcritureComptable vEcritureComptable;
    private JournalComptable journalComptable;

    /**
     * Before each test initialize the variables
     */
    @Before
    public void comptabiliteManagerImplSIT_Init(){
        manager = new ComptabiliteManagerImpl();
        vEcritureComptable = new EcritureComptable();
        journalComptable = new JournalComptable("AC", "Achat");
        vEcritureComptable.setJournal(journalComptable);
        vEcritureComptable.setDate(new Date());
    }

    /**
     * after each test reset the variables
     */
    @After
    public void ResetvEcritureComptable(){
        journalComptable = new JournalComptable();
        vEcritureComptable=new EcritureComptable();
    }

    /**
     * test on DB connection
     * @throws FunctionalException
     */
    @Test
    public void test1_ConnectioToDB() throws FunctionalException {

        List<EcritureComptable> ecritureComptableList=manager.getListEcritureComptable();
        Assert.assertNotEquals(ecritureComptableList.size(),null);

    }

}