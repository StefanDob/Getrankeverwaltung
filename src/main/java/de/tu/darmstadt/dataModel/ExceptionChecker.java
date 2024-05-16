package de.tu.darmstadt.dataModel;

import org.jetbrains.annotations.NotNull;

import java.util.function.Predicate;

/**
 * An {@link ExceptionChecker} is an abstract class that is used to handle any type of {@link Exception}s or
 * {@link Error}s.
 *
 * @author Toni Tan Phat Tran
 * @version 16.05.2024
 */
public final class ExceptionChecker {

    /**
     * Class {@link ExceptionChecker} cannot be instantiated.
     */
    private ExceptionChecker() {
        throw new RuntimeException("ExceptionChecker cannot be instantiated");
    }

    // ::::::::::::::::::::::::::::::::::::: METHODS ::::::::::::::::::::::::::::::::::::

    /**
     * This static method checks if a specified {@link Object} meets the requirements of a specified {@link Predicate}.
     * If not, an {@link Exception} is thrown.
     *
     * @param object the specified {@link Object}
     * @param predicate the specified {@link Predicate}
     * @param exception the specified {@link Exception}
     *
     * @return the specified {@link Object} is checked successfully
     *
     * @param <E> The type of {@link Exception} that may be thrown
     * @param <R> The type of objects that may be checked
     * @throws E is thrown if the specified {@link Predicate} is not met.
     */
    public static <E extends Exception, R>
    R check_if_instance_is_valid(R object, @NotNull Predicate<? super R> predicate, E exception) throws E
    {
        if( predicate.test(object) ) {
            throw exception;
        } // end of if

        return object;
    }


}
