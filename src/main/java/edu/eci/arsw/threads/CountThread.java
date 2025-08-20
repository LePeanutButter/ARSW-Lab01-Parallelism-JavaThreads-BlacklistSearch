/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.threads;

import lombok.Getter;
import lombok.Setter;

import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 *
 * @author hcadavid. lepeanutbutter. lanapequin.
 * @version August 20, 2025
 */
@Getter
@Setter
public class CountThread extends Thread {
    Logger logger = Logger.getLogger(getClass().getName());
    private final int a;
    private final int b;

    public CountThread(int a, int b) {
        this.a = a;
        this.b = b;
    }

    /**
     * Executes the thread logic by printing numbers from {@code a} to {@code b}
     * to the logger, separated by commas.
     */
    @Override
    public void run() {
         String results = IntStream.rangeClosed(a, b)
                .mapToObj(Integer::toString)
                .collect(Collectors.joining(", "));
        logger.info(results);
    }
}
