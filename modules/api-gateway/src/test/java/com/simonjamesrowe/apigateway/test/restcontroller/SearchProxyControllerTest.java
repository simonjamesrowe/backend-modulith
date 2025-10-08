package com.simonjamesrowe.apigateway.test.restcontroller;

import com.simonjamesrowe.component.test.BaseComponentTest;
import com.simonjamesrowe.component.test.ComponentTest;
import org.junit.jupiter.api.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@ComponentTest
class SearchProxyControllerTest extends BaseComponentTest {

    @Test
    void shouldProxyBlogsSearchRequest() {
        String mockResponse = """
            [
                {
                    "id": "1",
                    "title": "Blog 1",
                    "shortDescription": "Short desc 1",
                    "tags": ["tag1"],
                    "thumbnailImage": "thumb1.jpg",
                    "smallImage": "small1.jpg",
                    "mediumImage": "med1.jpg",
                    "createdDate": "2023-01-01"
                }
            ]
            """;

        stubFor(get(urlEqualTo("/blogs?q=kotlin"))
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", "application/json")
                .withBody(mockResponse)));

        given()
            .log().all()
            .accept("application/json")
            .queryParam("q", "kotlin")
            .when()
            .get("/search/blogs")
            .then()
            .log().all()
            .statusCode(200)
            .contentType("application/json")
            .body("[0].id", equalTo("1"))
            .body("[0].title", equalTo("Blog 1"));
    }

    @Test
    void shouldProxyGetAllBlogsRequest() {
        String mockResponse = """
            [
                {
                    "id": "1",
                    "title": "Blog 1",
                    "shortDescription": "Short desc 1",
                    "tags": ["tag1"],
                    "thumbnailImage": "thumb1.jpg",
                    "smallImage": "small1.jpg",
                    "mediumImage": "med1.jpg",
                    "createdDate": "2023-01-01"
                },
                {
                    "id": "2",
                    "title": "Blog 2",
                    "shortDescription": "Short desc 2",
                    "tags": ["tag2"],
                    "thumbnailImage": "thumb2.jpg",
                    "smallImage": "small2.jpg",
                    "mediumImage": "med2.jpg",
                    "createdDate": "2023-01-02"
                }
            ]
            """;

        stubFor(get(urlEqualTo("/blogs"))
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", "application/json")
                .withBody(mockResponse)));

        given()
            .log().all()
            .accept("application/json")
            .when()
            .get("/search/blogs")
            .then()
            .log().all()
            .statusCode(200)
            .contentType("application/json")
            .body("[0].id", equalTo("1"))
            .body("[1].id", equalTo("2"));
    }

    @Test
    void shouldProxySiteSearchRequest() {
        String mockResponse = """
            [
                {
                    "type": "Blogs",
                    "hits": [
                        {
                            "name": "Blog Title",
                            "imageUrl": "image1.jpg",
                            "link": "/blog/1"
                        }
                    ]
                },
                {
                    "type": "Jobs",
                    "hits": [
                        {
                            "name": "Job Title",
                            "imageUrl": "image2.jpg",
                            "link": "/job/1"
                        }
                    ]
                }
            ]
            """;

        stubFor(get(urlEqualTo("/site?q=Universal"))
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", "application/json")
                .withBody(mockResponse)));

        given()
            .log().all()
            .accept("application/json")
            .queryParam("q", "Universal")
            .when()
            .get("/search/site")
            .then()
            .log().all()
            .statusCode(200)
            .contentType("application/json")
            .body("[0].type", equalTo("Blogs"))
            .body("[0].hits[0].name", equalTo("Blog Title"))
            .body("[1].type", equalTo("Jobs"))
            .body("[1].hits[0].name", equalTo("Job Title"));
    }
}
