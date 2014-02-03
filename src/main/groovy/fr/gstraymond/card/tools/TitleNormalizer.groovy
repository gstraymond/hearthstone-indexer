package fr.gstraymond.card.tools

import java.text.Normalizer

class TitleNormalizer {
	
	static String normalize(String title) {
		def link = Normalizer.normalize(title, Normalizer.Form.NFKD)
		link = link.replace(' ', '-').replaceAll(/[^-\w]/, '')
		link.toLowerCase()
	}
}
