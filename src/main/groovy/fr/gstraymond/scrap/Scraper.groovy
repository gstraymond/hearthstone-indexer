package fr.gstraymond.scrap

import groovy.json.JsonBuilder
import groovy.json.JsonSlurper


class Scraper {

    String host = 'hearthhead.com'
    List languages = []

    def scrap() {
        languages.each { lang ->
            def url = "http://$lang.$host/cards"
            JsonBuilder builder = new JsonBuilder(buildJson(url).content + buildJson(url + '?filter=uc=on').content)

            new File("src/main/resources/scrap/scrapedCards.${lang}.json").withWriter {
                it << builder.toPrettyString()
            }
        }
    }

    def buildJson(def url) {
        println "scraping $url ..."

        def page = new URL(url).text

        def jsonText = page.split('var hearthstoneCards = ')[1].split('];')[0] + ']'
        jsonText = jsonText.replaceAll('popularity', '"popularity"')
        def json = new JsonSlurper().parseText(jsonText)
        new JsonBuilder(json)
    }
}