package com.example.joannahulek.inventoryapp.specifics;

import android.content.ContentValues;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Joasia on 17.08.2017.
 */
public class ProductTest {
    private Product product; //To bedziem testowac ;)

    @Before
    public void setUp() throws Exception {
        product = null;
    }

    @Test
    public void should_not_contains_product_id_in_content_values() {
        //given
        Integer id = 22223;
        product = new Product(id, irrelevantString(), irrelevantDouble(), irrelevantInteger(), irrelevantString(), irrelevantString(), irrelevantString());
        //when
        ContentValues values = product.transformToContentValues();
        assertEquals(null,values.get("id"));

    }

    private Integer irrelevantInteger() {
        return 2;
    }

    private Double irrelevantDouble() {
        return 22d;
    }

    private String irrelevantString() {
        return "";

    }

}