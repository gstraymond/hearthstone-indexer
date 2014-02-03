package fr.gstraymond.converter

import fr.gstraymond.card.Card
import fr.gstraymond.constant.Capabilities
import fr.gstraymond.constant.Classes
import fr.gstraymond.constant.Langs
import fr.gstraymond.constant.MinionTypes
import fr.gstraymond.constant.Rarities
import fr.gstraymond.constant.Sets
import fr.gstraymond.constant.Types

class CardConverter {

	def lang
	def scrapedCard
	
	Card card = new Card()
	
	Card parse() {
		setTitle()
		setDescription()
		setCastingCost()
		setAttack()
		setHealth()
		setMinionType()
		setClazz()
		setImage()
		setSet()
		setRarity()
		setType()
		setElite()
		setCapabilities()
		
		card
	}
	
	void setTitle() {
		card.title = scrapedCard.name
	}
	
	void setDescription() {
		card.description = scrapedCard.description
	}
	
	void setCastingCost() {
		card.castingCost = scrapedCard.cost
	}
	
	void setAttack() {
		card.attack = scrapedCard.attack
	}
	
	void setHealth() {
		card.health = scrapedCard.health
	}
	
	void setMinionType() {
		card.minionType = getMinionType(scrapedCard)
	}
	
	def getMinionType(scrapedCard) {
		if (! scrapedCard.race) {
			return null
		}
		
		if (! MinionTypes.MAP[lang]) {
			throw new RuntimeException("$scrapedCard.name : no minionTypes for lang $lang")
		}
		
		def race = MinionTypes.MAP[lang][scrapedCard.race]
		if (! race) {
			throw new RuntimeException("$scrapedCard.name : no minionTypes for lang $lang and type $scrapedCard.race")
		}
		
		race
	}
	
	void setClazz() {
		card.clazz = getClazz(scrapedCard)
	}
	
	def getClazz(scrapedCard) {
		if (! scrapedCard.classs) {
			return Classes.DEFAULT
		}
		
		if (! Classes.MAP[lang]) {
			throw new RuntimeException("$scrapedCard.name : no classes for lang $lang")
		}
		
		def clazz = Classes.MAP[lang][scrapedCard.classs]
		if (! clazz) {
			throw new RuntimeException("$scrapedCard.name : no classes for lang $lang and type $scrapedCard.classs")
		}
		
		clazz
	}
	
	void setImage () {
		def language = Langs.MAP[lang]
		if (! language) {
			throw new RuntimeException("$scrapedCard.name : no langs for lang $lang")
		}
		
		card.image = "http://wow.zamimg.com/images/hearthstone/cards/${language}/original/${scrapedCard.image}.png"
	}
	
	void setSet() {
		card.set = getSet(scrapedCard)
	}
	
	def getSet(scrapedCard) {
		if (! Sets.MAP[lang]) {
			throw new RuntimeException("$scrapedCard.name : no sets for lang $lang")
		}
		
		def set = Sets.MAP[lang][scrapedCard.set]
		if (! set) {
			throw new RuntimeException("$scrapedCard.name : no classes for lang $lang and type $scrapedCard.set")
		}
		
		set
	}
	
	void setRarity() {
		card.rarity = getRarity(scrapedCard)
	}
	
	def getRarity(scrapedCard) {
		if (! Rarities.MAP[lang]) {
			throw new RuntimeException("$scrapedCard.name : no rarities for lang $lang")
		}
		
		def rarity = Rarities.MAP[lang][scrapedCard.quality]
		if (! rarity) {
			throw new RuntimeException("$scrapedCard.name : no rarities for lang $lang and type $scrapedCard.quality")
		}
		
		rarity
	}
	
	void setType() {
		card.type = getType(scrapedCard)
	}
	
	def getType(scrapedCard) {
		if (! Types.MAP[lang]) {
			throw new RuntimeException("$scrapedCard.name : no types for lang $lang")
		}
		
		def type = Types.MAP[lang][scrapedCard.type]
		if (! type) {
			throw new RuntimeException("$scrapedCard.name : no types for lang $lang and type $scrapedCard.type")
		}
		
		type
	}
	
	void setElite() {
		if (scrapedCard.elite == 1) {
			card.elite = 'Elite'
		}
	}
	
	void setCapabilities() {
		card.capabilities = getCapabilities(scrapedCard)
	}
	
	List<String> getCapabilities(scrapedCard) {
		if (! Capabilities.LIST[lang]) {
			throw new RuntimeException("$scrapedCard.name : no capabilities for lang $lang")
		}
		
		def capabilities = []
		Capabilities.LIST[lang].each {
			if (scrapedCard.description?.contains(it)) {
				capabilities += it
			}
		}
		capabilities
	}
}
