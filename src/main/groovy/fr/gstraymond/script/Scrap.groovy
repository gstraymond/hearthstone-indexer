package fr.gstraymond.script

import fr.gstraymond.scrap.Scraper


println "Scraping !"

new Scraper(languages: ['en', 'fr']).scrap()
