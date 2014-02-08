package fr.gstraymond.script

import fr.gstraymond.constant.template.Translations;
import groovy.text.GStringTemplateEngine

def templateFile = new File('src/main/resources/template/index.template')
def engine = new GStringTemplateEngine()

Translations.LIST.each { translation ->
	def template = engine.createTemplate(templateFile).make(translation)
	new File("www/${translation.pageName}.html").withOutputStream { out ->
		out << template.toString()
	}
}