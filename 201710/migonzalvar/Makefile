.PHONY=test
test:
	@python -c 'import main;main.test()'
	@echo ----
	@python main.py 4 7
	@echo ----
	@flake8 main.py
	@mypy --strict main.py
