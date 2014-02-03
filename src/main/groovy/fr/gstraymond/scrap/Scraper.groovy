package fr.gstraymond.scrap

import groovy.json.JsonBuilder
import groovy.json.JsonSlurper


class Scraper {

	String host = 'hearthhead.com'
	List languages = []

	def scrap() {
		languages.each { lang ->
			def url = "http://$lang.$host/cards"
			println "scraping $url ..."
			
			def page = new URL(url).text
	
			def jsonText = page.split('var hearthstoneCards = ')[1].split('];')[0] + ']'
			jsonText = jsonText.replaceAll('popularity', '"popularity"')
			def json = new JsonSlurper().parseText(jsonText)
			def builder = new JsonBuilder(json)
			
			new File("src/main/resources/scrap/scrapedCards.${lang}.json").withWriter {
				 it << builder.toPrettyString()
			}
		}
	}
}