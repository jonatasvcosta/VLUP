import newspaper


def main(url):
    source = newspaper.build(url)
    first = source.articles[0]
    first.download()
    first.parse()
    print(first.title)
    print(first.text)
    # newspaper.news_pool.set([source], threads_per_source=2)
    # newspaper.news_pool.join()
    # source.articles


if __name__ == "__main__":
    main("http://www.reuters.com")
