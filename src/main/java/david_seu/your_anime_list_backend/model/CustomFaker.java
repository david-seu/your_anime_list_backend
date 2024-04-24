package david_seu.your_anime_list_backend.model;

import net.datafaker.Faker;

public class CustomFaker extends Faker {
    public FakeAnime anime(){
        return getProvider(FakeAnime.class, FakeAnime::new, this);
    }
}
