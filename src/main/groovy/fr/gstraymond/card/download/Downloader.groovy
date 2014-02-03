package fr.gstraymond.card.download

class Downloader {
	static pictureLocation = '/media/guillaume/Data/Dropbox/Public/hs'
	static pictureHost = 'http://dl.dropboxusercontent.com/u/22449802/hs'

	def url, fileFolderName, title
	
	def download() {
		// existence du répertoire
		def folderPath = "$pictureLocation/$fileFolderName"
		def fileFolder = new File(folderPath)
		if (!fileFolder.exists())  {
			fileFolder.mkdirs()
		}
		
		// existence du fichier
		def fileName = "$folderPath/$title"
		def file = new File(fileName)
		if (!file.exists())  {
			// téléchargement 
			println "Downloading $url to $fileName"
			file.withOutputStream { out ->
				out << new URL(url).openStream()
			}
		}
	}
}
