package fr.gstraymond.constant.template

class Translations {
	
	static LANGS = ['en', 'fr']

	static LIST = [
		[
			lang: 'en',
			langs: LANGS,
			pageName: 'index',
			head: [
				title: 'Hearthstone card search',
				description: 'Fast and powerful search for your Hearthstone cards. Helps you to build betters decks !'
			],
			body: [
				title: 'Hearthstone search engine'
			],
			facets: [
				castingCost: 'Casting cost',
				clazz: 'Class',
				attack: 'Attack',
				health: 'Health',
				minionType: 'Minion Type',
				set: 'Set',
				rarity: 'Rarity',
				type: 'Type',
				capabilities: 'Capabilities'
			]
		],
		[
			lang: 'fr',
			pageName: 'fr',
			langs: LANGS,
			head: [
				title: 'Recherche de cartes Hearthstone',
				description: 'Moteur de recherche rapide et puissant pour vos cartes HearthStone. Aide à construire de meilleurs jeux !'
			],
			body: [
				title: 'Recherche de cartes Hearthstone'
			],
			facets: [
				castingCost: 'Coût',
				clazz: 'Classe',
				attack: 'Attaque',
				health: 'Vie',
				minionType: 'Type de serviteur',
				set: 'Set',
				rarity: 'Rareté',
				type: 'Type',
				capabilities: 'Capacités'
			]
		]
	]
}
