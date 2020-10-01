package com.dummy.myerp.model.bean.comptabilite;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class CompteComptableTest {

    List<CompteComptable> compteComptables;

    @Before
    public void initCcompteComptables(){
        CompteComptable compteComptable=new CompteComptable();
        compteComptable.setLibelle("1er compte");
        compteComptable.setNumero(1);
        compteComptables =new ArrayList<>();
        compteComptables.add(compteComptable);
    }

    /**
     * After each test reset object comptes
     */
    @After
    public void ResetcompteComptables(){

        List<CompteComptable> compteComptables = new ArrayList<>();
    }

    /**
     * test on method GetByNumero
     */
    @Test
    public void checkGetByNumero(){


        Assert.assertEquals(CompteComptable.getByNumero(compteComptables,1).getLibelle(),"1er compte");
        Assert.assertEquals(CompteComptable.getByNumero(compteComptables, 1).getNumero().intValue(), 1);

    }

    /**
     * test on method GetByNumero, in a list of 1 get number 2, expect null CompteComptable
     */
    @Test
    public void checkGetByNumeroIsNullReturn(){

        Assert.assertEquals(CompteComptable.getByNumero(compteComptables,2),null);


    }

}
