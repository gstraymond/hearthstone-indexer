package fr.gstraymond.elasticsearch

import static groovyx.net.http.ContentType.JSON
import fr.gstraymond.card.tools.TitleNormalizer
import groovy.json.JsonBuilder
import groovyx.net.http.HttpResponseException
import groovyx.net.http.RESTClient

class ESIndexer {
	def host
	def port = 9200
	def protocol = 'http'
	def lang

	def getRestClient() {
		new RESTClient("${protocol}://${host}:${port}/")
	}

	void index(obj) {
		try {
			def jsonBuilder = new JsonBuilder(obj)
			def title = TitleNormalizer.normalize(obj.title)
			def resp = restClient.post(
					path: "hearthstone/card_$lang/$title",
					body: jsonBuilder.toPrettyString(),
					requestContentType : JSON)
		} catch(HttpResponseException e) {
			e.printStackTrace();
		}
	}

	void clear() {
		try {
			def resp = restClient.delete(path: 'hearthstone')
		} catch(HttpResponseException e) {
			e.printStackTrace();
		}
	}

	void configure() {
//		try {
//			println "configuring..."
//			println getESConfiguration().toPrettyString()
//			def resp = restClient.put(
//					path: 'hearthstone',
//					body: getESConfiguration().toPrettyString(),
//					requestContentType : JSON)
//		} catch(HttpResponseException e) {
//			println e.response.data
//			e.printStackTrace();
//		}
	}
	
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
	
	def multiFieldList = [
		'abilities',
		'artists',
		'colors',
		'editions',
		'formats'
	]

	def getESConfiguration() {
		def data = [
			settings: [
				analysis: [
					analyzer: [
						englishAnalyzer: [
							tokenizer: 'standard',
							filter: ['standard', 'lowercase', 'asciifolding', 'englishStemmer', 'frenchElision']
						],
						default: [
							tokenizer: 'standard',
							filter: ['standard', 'lowercase', 'asciifolding', 'frenchStemmer', 'frenchElision']
						],
						tagAnalyzer: [
							tokenizer: 'standard',
							filter: ['lowercase']
						]
					],
					filter: [
						englishStemmer: [
							type: 'stemmer',
							name: 'english'
						],
						frenchStemmer: [
							type: 'stemmer',
							name: 'light_french'
						],
						frenchElision: [
							type: 'elision',
							articles: ['l', 'm', 't', 'qu', 'n', 's', 'j', 'd']
						]
					]
				]
			],
			mappings: [
				card: [
					dynamic_templates: [
						[
							titleTemplate: [
								match: 'title',
								match_mapping_type: 'string',
								mapping: [
									analyzer: 'englishAnalyzer',
									include_in_all: true
								]
							]
						],
						[
							descriptionTemplate: [
								match: 'description',
								match_mapping_type: 'string',
								mapping: [
									analyzer: 'englishAnalyzer',
									include_in_all: true
								]
							]
						],
						[
							typeTags: [
								match: 'type',
								match_mapping_type: 'string',
								mapping: [
									analyzer: 'tagAnalyzer',
									include_in_all: true
								]
							]
						],
						[
							raritiesTags: [
								match: 'rarities',
								match_mapping_type: 'string',
								mapping: [
									analyzer: 'tagAnalyzer',
									include_in_all: true
								]
							]
						]
					],
					properties: multiFieldList.collectEntries {
						[(it): getMultiFieldConf(it)]
					}
				]
			]
		]
		new JsonBuilder(data)
	}
}