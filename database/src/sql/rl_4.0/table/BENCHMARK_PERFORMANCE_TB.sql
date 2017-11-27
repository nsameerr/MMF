CREATE TABLE `benchmark_performance_tb` (
  `id` INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  `benchmark` INTEGER UNSIGNED,
  `datetime` TIMESTAMP,
  `open` DECIMAL(8,2),
  `high` DECIMAL(8,2),
  `low` DECIMAL(8,2),
  `close` DECIMAL,
  `sharestraded` NUMERIC,
  `turnover` DECIMAL(8,2),
  PRIMARY KEY (`id`)
);
