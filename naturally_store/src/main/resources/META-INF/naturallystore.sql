-- Database: naturallystore

DROP DATABASE IF EXISTS `naturallystore`;
CREATE DATABASE `naturallystore`;
USE `naturallystore`;
--
-- Table structure for table `product`
--
DROP TABLE IF EXISTS `product`;

CREATE TABLE `product` (
  `productCode` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `manufacturer` varchar(45) DEFAULT NULL,
  `suitableFor` varchar(45) DEFAULT NULL,
  `skinType` varchar(45) DEFAULT NULL,
  `capacity` varchar(45) DEFAULT NULL,
  `description` blob,
  `picture` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`productCode`)
);

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
INSERT INTO `product` VALUES 
(1,'Cocoa body lotion','Ziaja','woman&man','dry and normal skin','200 ml', 'Intensely moisturises and regenerates dry and normal skin. Visibly softens, smoothes and makes the skin more elastic. Eliminates the feeling of roughness and effectively soothes irritations.','cocoa_body_lotion.jpg'),
(2,'Baby & kids lubricating bath','Ziaja','baby&kids','NA','370 ml', 'Gently cleanses the skin. Lightly lubricates, leaving a light protection layer on the skin. Softens the epidermis and prevents skin dehydration.','baby&kids_lubricating_bath.jpg'),
(3,'blueberry & currant shower gel','Ziaja', 'woman&man','all types','500 ml', 'Moisturises the skin and protects it from dehydration. Soothes irritations and softens the skin. Has refreshing fruity fragrance.','blueberry&currant_shower_gel.jpg'),
(4,'Pure Natural Coconut Oil','ORGANIQUE', 'woman&man','all types','100 ml', 'enhance elasticity, nourish, hydrate, smooth.','Coconut_Oil.jpg'),
(5,'Himalayan Camellia Pore Minimizing Mask','Leaders', 'woman&man','all types','1 mask', 'Advanced Bio-Cellulose Mask; Visibly Minimizes Pores; Refines Rough Skin; Soothes Troubled and Irritated; Skin Protectant','Himalayan_Camellia_Pore_Minimizing_Mask.jpg'),
(6,'Anti Aging Serum with aloe and aladania','Bioaroma', 'woman&man','all types','30 ml', 'eliminate wrinkles (lines around the corner of your eyes), have a smouther face - soft touch, be extremely happy with the results of your anti aging serum and have an anti aging product that works','Serum_Anti_Aging _Aloe_Aladania.jpg'),
(7,'Hand Cream with Argan & Pomegranate','Aphrodite', 'woman&man','all types','75 ml', 'Active polysaccharides and olive oil have a unique feature of moisture maintenance and thus efficiently deal with skin hydration, facilitate its elasticity and firmness. Vitamin complex and argan butter protect your skin from hazardous environmental influences, nourish, soften and smooth the epithelium layer. Acai and inca inchi butter reduce dryness and prevent premature hand skin withering. They penetrate deep into the derma layer for efficient nourishing and moisturizing effect. Extract of pomegranate has a strong toning and antioxidant effect, nourishing and rejuvenating your skin.','Hand_Cream_with_pOMEGRANATE.png'),
(8,'Natural restructuring hair mask','Bioselect', 'woman&man','NA','200 ml', 'This hair mask by BIOselect moisturizes and repairs tired and damaged hair - dry or coloured - using a robust natural composition of Dictamelia, avocado oil, olive leaf extract, laurel and sage to nourish, hydrate, strengthen the natural resistance of hair, increase elasticity and provide maximum protection of the hair shaft from UV and environmental factors.','Natural_restructuring _hair_mask.png'),
(9,'Eucalyptus essential oil','Bioaroma', 'woman&man','NA','5 ml', 'Breathe freely during colds by adding 8 drops of eucalyptus essential oil in a bowl with boiling water and inhale the vapors.','Eucalyptus_Oil.png'),
(10,'Lip Balm with Mango scent','Aphrodite', 'woman&man','NA','4 g', 'Try natural lip balm by Aphrodite with a pleasant sweet mango flavour. It perfectly moisturizes dry and cracked lips and gives them an attractive healthy look.','Aphrodite-Mango-Lip-Balm.jpg');

UNLOCK TABLES;
SELECT * FROM product;