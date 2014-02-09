package fr.gstraymond.elasticsearch

import static groovyx.net.http.ContentType.JSON
import fr.gstraymond.card.tools.TitleNormalizer
import groovy.json.JsonBuilder
import groovyx.net.http.HttpResponseException
import groovyx.net.http.RESTClient

class ESIndexer {
	def host
	def lang
	
	static port = 9200
	static protocol = 'http'
	static INDEX = 'hearthstone'

	def getRestClient() {
		new RESTClient("${protocol}://${host}:${port}/")
	}

	void index(obj) {
		try {
			def jsonBuilder = new JsonBuilder(obj)
			def title = TitleNormalizer.normalize(obj.title)
			def resp = restClient.post(
					path: "$path/card/$title",
					body: jsonBuilder.toString(),
					requestContentType : JSON)
		} catch(HttpResponseException e) {
			e.printStackTrace();
		}
	}

	void clear() {
		try {
			def resp = restClient.delete(path: path)
		} catch(HttpResponseException e) {
			e.printStackTrace();
		}
	}

	void configure() {
		try {
			def conf = new ESConfig(lang: lang).getESConfiguration()
			println "configuring... ${conf.toPrettyString()}"
			def resp = restClient.put(
					path: path,
					body: conf.toString(),
					requestContentType : JSON)
		} catch(HttpResponseException e) {
			e.printStackTrace();
		}
	}
	
	def getPath() {
		INDEX + '_' + lang
	}
}