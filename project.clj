(defproject ds-server "0.1.0"
  :description "Draconic Systems Server"
  :url "https://draconic-systems.com"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [;; token hashing
                 [buddy "2.0.0"]

                 [camel-snake-kebab "0.4.2"]

                 [clj-http "3.10.3"]

                 ;; redis
                 [com.taoensso/carmine "3.0.1"]

                 ;; logging
                 [com.taoensso/timbre "5.1.0"]

                 ;; read environment variables
                 [environ "1.2.0"]

                 ;; HTML engine
                 [hiccup "1.0.5"]

                 ;; coordinating
                 [integrant "0.8.0"]

                 ;; format coercion
                 [metosin/muuntaja "0.6.7"]

                 ;; routing
                 [metosin/reitit "0.5.10"]

                 ;; clojure
                 [org.clojure/clojure "1.10.1"]

                 ;; jetty server
                 [ring/ring-core "1.8.2"]
                 [ring/ring-jetty-adapter "1.8.2"]]

  :plugins [[gorillalabs/lein-docker "1.3.0"]
            [lein-environ "1.2.0"]
            [lein-shell "0.5.0"]]

  :jvm-opts ["-Xms2g" "-Xmx2g"]

  :docker {:image-name "draconic-systems.com/ds-server"
           :tags ["%s" "latest"]}

  :deploy-branches ["master"]

  :main ^:skip-aot ds.system

  :target-path "target/%s"

  :profiles {:uberjar {:aot :all
                       :uberjar-name "ds-server.jar"
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}
             :dev {:repl-options {:init (load-file "dev/user.clj")
                                  :init-ns user}
                   :dependencies [[integrant/repl "0.3.2"]
                                  [org.clojure/test.check "1.1.0"]
                                  [ring/ring-devel "1.8.2"]
                                  [slamhound "1.5.5"]]
                   :plugins [ ;; code coveragge
                             [lein-cloverage "1.2.0"]

                             ;; linting
                             [jonase/eastwood "0.3.10"]

                             ;; linting/best practice
                             [lein-kibit "0.1.8"]]}
             :kaocha {:dependencies [[lambdaisland/kaocha "1.0.700"]]}}
  :aliases {;; Kaocha testing aliases
            "kaocha" ["with-profile" "+kaocha" "run" "-m" "kaocha.runner"]
            "ci" ["do"
                  ["kaocha" "--focus" ":unit"]
                  ["shell" "cowsay" "Skipping :integration for now"]]

            "slamhound" ["run" "-m" "slam.hound"]
            "test" "kaocha"

            ;; DB migrations
            "migrate" ["migrate-up"]
            "migrate-up" ["shell" "java" "-classpath" "target/uberjar/ds-server.jar" "ds.util.migration_helper" "up"]
            "migrate-down" ["shell" "java" "-classpath" "target/uberjar/ds-server.jar" "ds.util.migration_helper" "down"]})
