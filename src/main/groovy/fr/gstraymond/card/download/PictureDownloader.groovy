package fr.gstraymond.card.download

import fr.gstraymond.card.Card
import fr.gstraymond.card.tools.TitleNormalizer

class PictureDownloader {

	Card card
	def lang

	def download() {

		def cardTitle = formatTitle(card)

		new Downloader(
			url: card.image,
			fileFolderName: "$lang/${card.set.replaceAll(" ", "-")}",
			title: cardTitle
		).download()

		// mis à jour du chemin
		card.image = "$Downloader.pictureHost/$lang/${card.set.replaceAll(" ", "-")}/$cardTitle"
	}

	def formatTitle(Card card) {
		TitleNormalizer.normalize(card.title) + '.png'
	}
}