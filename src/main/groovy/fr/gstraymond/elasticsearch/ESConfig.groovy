package fr.gstraymond.elasticsearch

import groovy.json.JsonBuilder

class ESConfig {
	
	def lang
	
	static langConf = [
		en: [
			stemmer: 'english',
			elision: ['l', 'm', 't', 'qu', 'n', 's', 'j', 'd']
		],
		fr: [
			stemmer: 'light_french',
			elision: ['l', 'm', 't', 'qu', 'n', 's', 'j', 'd']
		]
	]
	
	def getConf(field) {
		langConf[lang][field]
	}
	
	
	static multiFieldList = [
		'capabilities',
		'clazz',
		'minionType',
		'rarity',
		'set',
		'type'
	]
	
	def getMultiFieldConf(field) {
		[
			type: 'multi_field',
			fields: [
				"$field": [
					type: 'string',
					index: 'analyzed'
				],
				exact: [
					type: 'string',
					index: 'not_analyzed'
				]
			]
		]
	}

	def getESConfiguration() {
		new JsonBuilder([
			settings: [
				analysis: [
					analyzer: [
						default: [
							tokenizer: 'standard',
							filter: ['standard', 'lowercase', 'asciifolding', 'stemmer', 'elision']
						],
						tagAnalyzer: [
							tokenizer: 'standard',
							filter: ['lowercase']
						]
					],
					filter: [
						stemmer: [
							type: 'stemmer',
							name: getConf('stemmer')
						],
						elision: [
							type: 'elision',
							articles: getConf('elision')
						]
					]
				]
			],
			mappings: [
				card: [
					properties: multiFieldList.collectEntries {
						[(it): getMultiFieldConf(it)]
					}
				]
			]
		])
	}
}
