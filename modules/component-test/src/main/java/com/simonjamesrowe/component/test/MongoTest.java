package com.simonjamesrowe.component.test;

import com.simonjamesrowe.component.test.mongo.WithMongoDBContainer;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Sets up a redis container
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@WithMongoDBContainer
@DataMongoTest
public @interface MongoTest {

}
