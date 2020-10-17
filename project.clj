(defproject ds-server "0.1.0"
  :description "Draconic Systems Server"
  :url "https://draconic-systems.com"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [;; token hashing
                 [buddy "2.0.0"]

                 ;; logging
                 [com.taoensso/timbre "5.0.1"]

                 ;; read environment variables
                 [environ "1.2.0"]

                 ;; coordinating
                 [integrant "0.8.0"]

                 ;; format coercion
                 [metosin/muuntaja "0.6.7"]

                 ;; routing
                 [metosin/reitit "0.5.5"]

                 ;; clojure
                 [org.clojure/clojure "1.10.1"]

                 ;; jetty server
                 [ring/ring-jetty-adapter "1.8.1"]]

  ;; System properties
  :env {:port 8080}
  :plugins [[gorillalabs/lein-docker "1.3.0"]
            [lein-immutant "2.1.0"]]

  :docker {:image-name "draconic-systems.com/ds-server"
           :tags ["%s" "latest"]}

  :_release-tasks [["vcs" "assert-committed"]
                   ["change" "version"
                    "leiningen.release/bump-version" "release"]
                   ["vcs" "commit"]
                   ["vcs" "tag"]
                   ["clean"]
                   ["uberjar"]
                   ["docker" "build"]
                   ["docker" "push"]
                   ["change" "version"
                    "leiningen.release/bump-version"]
                   ["vcs" "commit"]
                   ["vcs" "push"]]

  :deploy-branches ["master"]

  :main ^:skip-aot ds.system
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all
                       :uberjar-name "ds-server.jar"
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}
             :dev {:repl-options {:init (load-file "dev/user.clj")
                                  :init-ns user}
                   :dependencies [[integrant/repl "0.3.1"]
                                  [org.clojure/test.check "0.9.0"]
                                  [slamhound "1.5.5"]]
                   :plugins [;; code coveragge
                             [lein-cloverage "1.2.0"]

                             ;; linting
                             [jonase/eastwood "0.3.10"]

                             ;; linting/best practice
                             [lein-kibit "0.1.8"]]}
             :kaocha {:dependencies [[lambdaisland/kaocha "1.0.700"]]}}
  :aliases {"kaocha" ["with-profile" "+kaocha" "run" "-m" "kaocha.runner"]
            "slamhound" ["run" "-m" "slam.hound"]
            "test" "kaocha"})
