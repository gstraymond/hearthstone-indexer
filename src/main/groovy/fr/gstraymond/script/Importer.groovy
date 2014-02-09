package fr.gstraymond.script

import fr.gstraymond.card.download.PictureDownloader
import fr.gstraymond.converter.CardConverter
import fr.gstraymond.elasticsearch.ESIndexer
import groovy.json.JsonSlurper
import groovyx.gpars.GParsPool

def enableDebug = false
def index = true
def host = 'localhost'
def languages = ['en', 'fr']

languages.each { lang ->
	def file = new File("src/main/resources/scrap/scrapedCards.${lang}.json")
	def fullScrapedCards = new JsonSlurper().parse(file)
	
	def cards = fullScrapedCards.collect { scrapedCard ->
		new CardConverter(lang: lang, scrapedCard: scrapedCard).parse()
	}
	
	if (enableDebug) {
		cards.eachWithIndex { card, i ->
			println """$i
$card.title
$card.description
$card.castingCost
$card.attack
$card.health
$card.race
$card.clazz

"""
		}
	}

	if (index) {
		def indexer = new ESIndexer(host: host, lang: lang)
		indexer.clear()
		indexer.configure()
		
		GParsPool.withPool(4) {
			cards.eachWithIndexParallel { card, i ->
	//		cards.eachWithIndex { card, i ->
				println "indexing ${i+1} $card.title"
				new PictureDownloader(lang: lang, card: card).download()
				indexer.index(card)
			}
		}
	}
}
