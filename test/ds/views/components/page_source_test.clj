(ns ds.views.components.page-source-test
  (:require
   [clojure.test :refer :all]
   [ds.views.components.page-source :as page-source]))

(deftest pre-element-test
  (testing "returns a [:pre] element"
    (is (re-matches #"\[:pre .*\]" (str (page-source/pre "ds.views.components.page-source-test"))))))

(deftest footer-element-test
  (testing "returns a [:footer] element"
    (is (re-matches #"\[:footer.*\]" (str (page-source/footer "ds.views.components.page-source-test"))))))


(deftest raw-source-test
  (is (nil? (page-source/raw-source "ds.fake"))))
